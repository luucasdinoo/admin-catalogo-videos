package com.dino.admin.catalogo.domain.video;

import com.dino.admin.catalogo.domain.AggregateRoot;
import com.dino.admin.catalogo.domain.castmember.CastMemberID;
import com.dino.admin.catalogo.domain.category.CategoryId;
import com.dino.admin.catalogo.domain.event.DomainEvent;
import com.dino.admin.catalogo.domain.genre.GenreID;
import com.dino.admin.catalogo.domain.utils.InstantUtils;
import com.dino.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.time.Year;
import java.util.*;

public class Video extends AggregateRoot<VideoID> {

    private String title;
    private String description;
    private Year launchedAt;
    private double duration;
    private Rating rating;

    private boolean opened;
    private boolean published;

    private Instant createdAt;
    private Instant updatedAt;

    private ImageMedia banner;
    private ImageMedia thumbnail;
    private ImageMedia thumbnailHalf;

    private AudioVideoMedia trailer;
    private AudioVideoMedia video;

    private Set<CategoryId> categories;
    private Set<GenreID> genres;
    private Set<CastMemberID> castMembers;

     private Video(
             final VideoID anId,
             final String aTitle,
             final String aDescription,
             final Year aLaunchYear,
             final double aDuration,
             final boolean wasOpened,
             final boolean wasPublished,
             final Rating aRating,
             final Instant aCreatedAt,
             final Instant aUpdatedAt,
             final ImageMedia aBanner,
             final ImageMedia aThumb,
             final ImageMedia aThumbHalf,
             final AudioVideoMedia aTrailer,
             final AudioVideoMedia aVideo,
             final Set<CategoryId> categories,
             final Set<GenreID> genres,
             final Set<CastMemberID> members,
             final List<DomainEvent> domainEvents
             ) {
        super(anId, domainEvents);
        this.title = aTitle;
        this.description = aDescription;
        this.launchedAt = aLaunchYear;
        this.duration = aDuration;
        this.opened = wasOpened;
        this.published = wasPublished;
        this.rating = aRating;
        this.createdAt = aCreatedAt;
        this.updatedAt = aUpdatedAt;
        this.banner = aBanner;
        this.thumbnail = aThumb;
        this.thumbnailHalf = aThumbHalf;
        this.trailer = aTrailer;
        this.video = aVideo;
        this.categories = categories;
        this.genres = genres;
        this.castMembers = members;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new VideoValidator(this, handler).validate();
    }

    public static Video newVideo(
            final String aTitle,
            final String aDescription,
            final Year aLaunchYear,
            final double aDuration,
            final boolean wasOpened,
            final boolean wasPublished,
            final Rating aRating,
            final Set<CategoryId> categories,
            final Set<GenreID> genres,
            final Set<CastMemberID> members
    ){
        VideoID anId = VideoID.unique();
        final var now = InstantUtils.now();
        return new Video(
                anId,
                aTitle,
                aDescription,
                aLaunchYear,
                aDuration,
                wasOpened,
                wasPublished,
                aRating,
                now,
                now,
                null,
                null,
                null,
                null,
                null,
                categories,
                genres,
                members,
                null
        );
    }

    public static Video with(
            final VideoID anId,
            final String aTitle,
            final String aDescription,
            final Year aLaunchYear,
            final double aDuration,
            final boolean wasOpened,
            final boolean wasPublished,
            final Rating aRating,
            final Instant aCreatedAt,
            final Instant aUpdatedAt,
            final ImageMedia aBanner,
            final ImageMedia aThumb,
            final ImageMedia aThumbHalf,
            final AudioVideoMedia aTrailer,
            final AudioVideoMedia aVideo,
            final Set<CategoryId> categories,
            final Set<GenreID> genres,
            final Set<CastMemberID> members
    ){
        return new Video(
            anId,
            aTitle,
            aDescription,
            aLaunchYear,
            aDuration,
            wasOpened,
            wasPublished,
            aRating,
            aCreatedAt,
            aUpdatedAt,
            aBanner,
            aThumb,
            aThumbHalf,
            aTrailer,
            aVideo,
            categories,
            genres,
            members,
            null
        );
    }

    public static Video with(final Video aVideo){
        return new Video(
                aVideo.getId(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getLaunchedAt(),
                aVideo.getDuration(),
                aVideo.getOpened(),
                aVideo.getPublished(),
                aVideo.getRating(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt(),
                aVideo.getBanner().orElse(null),
                aVideo.getThumbnail().orElse(null),
                aVideo.getThumbnailHalf().orElse(null),
                aVideo.getTrailer().orElse(null),
                aVideo.getVideo().orElse(null),
                new HashSet<>(aVideo.getCategories()),
                new HashSet<>(aVideo.getGenres()),
                new HashSet<>(aVideo.getCastMembers()),
                aVideo.getDomainEvents()
        );
    }

    public Video update(
            final String aTitle,
            final String aDescription,
            final Year aLaunchYear,
            final double aDuration,
            final boolean wasOpened,
            final boolean wasPublished,
            final Rating aRating,
            final Set<CategoryId> categories,
            final Set<GenreID> genres,
            final Set<CastMemberID> members
    ){
        this.title = aTitle;
        this.description = aDescription;
        this.launchedAt = aLaunchYear;
        this.duration = aDuration;
        this.opened = wasOpened;
        this.published = wasPublished;
        this.rating = aRating;
        this.setCategories(categories);
        this.setGenres(genres);
        this.setCastMembers(members);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public void processing(final VideoMediaType aType) {
        if (VideoMediaType.VIDEO == aType){
            getVideo().ifPresent(media -> updateVideoMedia(media.processing()));

        } else if (VideoMediaType.TRAILER == aType){
            getTrailer().ifPresent(media -> updateTrailerMedia(media.processing()));

        }
    }

    public void completed(final VideoMediaType aType, final String encodedPath) {
        if (VideoMediaType.VIDEO == aType){
            getVideo().ifPresent(media -> updateVideoMedia(media.completed(encodedPath)));

        } else if (VideoMediaType.TRAILER == aType){
            getTrailer().ifPresent(media -> updateTrailerMedia(media.completed(encodedPath)));

        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Year getLaunchedAt() {
        return launchedAt;
    }

    public double getDuration() {
        return duration;
    }

    public Rating getRating() {
        return rating;
    }

    public boolean getOpened() {
        return opened;
    }

    public boolean getPublished() {
        return published;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Optional<ImageMedia> getBanner() {
        return Optional.ofNullable(banner);
    }

    public Optional<ImageMedia> getThumbnail() {
        return Optional.ofNullable(thumbnail);
    }

    public Optional<ImageMedia> getThumbnailHalf() {
        return Optional.ofNullable(thumbnailHalf);
    }

    public Optional<AudioVideoMedia> getTrailer() {
        return Optional.ofNullable(trailer);
    }

    public Optional<AudioVideoMedia> getVideo() {
        return Optional.ofNullable(video);
    }

    public Set<CategoryId> getCategories() {
        return categories != null ? Collections.unmodifiableSet(categories) : Collections.emptySet();
    }

    public Set<GenreID> getGenres() {
        return genres != null ? Collections.unmodifiableSet(genres) : Collections.emptySet();
    }

    public Set<CastMemberID> getCastMembers() {
        return castMembers != null ? Collections.unmodifiableSet(castMembers) : Collections.emptySet() ;
    }


    public Video updateBannerMedia(final ImageMedia banner) {
        this.banner = banner;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Video updateThumbnailMedia(final ImageMedia thumbnail) {
        this.thumbnail = thumbnail;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Video updateThumbnailHalfMedia(final ImageMedia thumbnailHalf) {
        this.thumbnailHalf = thumbnailHalf;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Video updateTrailerMedia(final AudioVideoMedia trailer) {
        this.trailer = trailer;
        this.updatedAt = InstantUtils.now();
        onAudioVideoMediaUpdated(trailer);
        return this;
    }

    public Video updateVideoMedia(final AudioVideoMedia video) {
        this.video = video;
        this.updatedAt = InstantUtils.now();
        onAudioVideoMediaUpdated(video);
        return this;
    }

    private void setCategories(final Set<CategoryId> categories) {
        this.categories = categories != null ? new HashSet<>(categories) : Collections.emptySet();
    }

    private void setGenres(final Set<GenreID> genres) {
        this.genres = genres != null ? new HashSet<>(genres) : Collections.emptySet();
    }

    private void setCastMembers(final Set<CastMemberID> castMembers) {
        this.castMembers = castMembers != null ? new HashSet<>(castMembers) : Collections.emptySet();
    }

    private void onAudioVideoMediaUpdated(final AudioVideoMedia media) {
        if (media != null && media.isPendingEncode()){
            this.registerEvent(new VideoMediaCreated(getId().getValue(), media.rawLocation()));
        }
    }
}
