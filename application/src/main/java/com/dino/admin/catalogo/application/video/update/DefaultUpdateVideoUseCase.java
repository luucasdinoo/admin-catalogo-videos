package com.dino.admin.catalogo.application.video.update;

import com.dino.admin.catalogo.domain.Identifier;
import com.dino.admin.catalogo.domain.castmember.CastMemberGateway;
import com.dino.admin.catalogo.domain.castmember.CastMemberID;
import com.dino.admin.catalogo.domain.category.CategoryGateway;
import com.dino.admin.catalogo.domain.category.CategoryId;
import com.dino.admin.catalogo.domain.exceptions.DomainException;
import com.dino.admin.catalogo.domain.exceptions.InternalErrorException;
import com.dino.admin.catalogo.domain.exceptions.NotFoundException;
import com.dino.admin.catalogo.domain.exceptions.NotificationException;
import com.dino.admin.catalogo.domain.genre.GenreGateway;
import com.dino.admin.catalogo.domain.genre.GenreID;
import com.dino.admin.catalogo.domain.validation.Error;
import com.dino.admin.catalogo.domain.validation.ValidationHandler;
import com.dino.admin.catalogo.domain.validation.handler.Notification;
import com.dino.admin.catalogo.domain.video.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultUpdateVideoUseCase extends UpdateVideoUseCase{

    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;
    private final CastMemberGateway castMemberGateway;
    private final VideoGateway videoGateway;
    private final MediaResourceGateway mediaResourceGateway;

    public DefaultUpdateVideoUseCase(
            final CategoryGateway categoryGateway,
            final GenreGateway genreGateway,
            final CastMemberGateway castMemberGateway,
            final VideoGateway videoGateway,
            final MediaResourceGateway mediaResourceGateway
    ) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
        this.videoGateway = Objects.requireNonNull(videoGateway);
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
    }

    @Override
    public UpdateVideoOutput execute(final UpdateVideoCommand aCommand) {
        final var anId = VideoID.from(aCommand.id());

        final var aRating = Rating.of(aCommand.rating())
                .orElseThrow(() -> DomainException.with(new Error("Rating not found %s".formatted(aCommand.rating()))));

        final var categories = toIdentifier(aCommand.categories(), CategoryId::from);
        final var genres = toIdentifier(aCommand.genres(), GenreID::from);
        final var members = toIdentifier(aCommand.members(), CastMemberID::from);

        Video aVideo = this.videoGateway.findById(anId)
                .orElseThrow(() -> NotFoundException.with(Video.class, anId));

        final var notification = Notification.create();
        notification.append(validateCategories(categories));
        notification.append(validateGenres(genres));
        notification.append(validateMembers(members));

        aVideo.update(
                aCommand.title(),
                aCommand.description(),
                Year.of(aCommand.launchedAt()),
                aCommand.duration(),
                aCommand.opened(),
                aCommand.published(),
                aRating,
                categories,
                genres,
                members
        );

        aVideo.validate(notification);
        if (notification.hasError()){
            throw new NotificationException("Could not update Aggregate Video", notification);
        }

        return UpdateVideoOutput.from(update(aCommand, aVideo));
    }

    private Video update(final UpdateVideoCommand aCommand, final Video aVideo){
        VideoID anId = aVideo.getId();

        try {
            final var aVideoMedia = aCommand.getVideo()
                    .map(it -> this.mediaResourceGateway.storeAudioVideo(anId, VideoResource.with(it, VideoMediaType.VIDEO)))
                    .orElse(null);

            final var aTrailerMedia= aCommand.getTrailer()
                    .map(it -> this.mediaResourceGateway.storeAudioVideo(anId, VideoResource.with(it, VideoMediaType.TRAILER)))
                    .orElse(null);

            final var aBanner = aCommand.getBanner()
                    .map(it -> this.mediaResourceGateway.storeImage(anId, VideoResource.with(it, VideoMediaType.BANNER)))
                    .orElse(null);

            final var aThumbnail = aCommand.getThumbnail()
                    .map(it -> this.mediaResourceGateway.storeImage(anId, VideoResource.with(it, VideoMediaType.THUMBNAIL)))
                    .orElse(null);

            final var aThumbHalf = aCommand.getThumbnailHalf()
                    .map(it -> this.mediaResourceGateway.storeImage(anId, VideoResource.with(it, VideoMediaType.THUMBNAIL_HALF)))
                    .orElse(null);

            return this.videoGateway.update(
                    aVideo.setVideo(aVideoMedia)
                            .setTrailer(aTrailerMedia)
                            .setBanner(aBanner)
                            .setThumbnail(aThumbnail)
                            .setThumbnailHalf(aThumbHalf)
            );

        }catch (final Throwable t){
            throw InternalErrorException.with(
                    "An error on create video was observed [videoId:%s]".formatted(anId.getValue()), t);
        }
    }

    private <T> Set<T> toIdentifier(final Set<String> ids, final Function<String, T> mapper){
        return ids.stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }

    private ValidationHandler validateCategories(final Set<CategoryId> ids){
        return validateAggregate("categories", ids, categoryGateway::existsByIds);
    }

    private ValidationHandler validateGenres(final Set<GenreID> ids){
        return validateAggregate("genres", ids, genreGateway::existsByIds);
    }

    private ValidationHandler validateMembers(final Set<CastMemberID> ids){
        return validateAggregate("cast members", ids, castMemberGateway::existsByIds);
    }

    private <T extends Identifier> ValidationHandler validateAggregate(
            final String aggregate,
            final Set<T> ids,
            final Function<Iterable<T>, List<T>> existsByIds
    ){
        final var notification = Notification.create();
        if (ids == null || ids.isEmpty()) {
            return notification;
        }

        final var retrievedIds = existsByIds.apply(ids);

        if (ids.size() != retrievedIds.size()) {
            final var missingIds = new ArrayList<>(ids);
            missingIds.removeAll(retrievedIds);

            final var missingIdsMessage = missingIds.stream()
                    .map(Identifier::getValue)
                    .collect(Collectors.joining(", "));

            notification.append(new Error("Some %s could not be found: %s".formatted(aggregate, missingIdsMessage)));
        }

        return notification;
    }
}
