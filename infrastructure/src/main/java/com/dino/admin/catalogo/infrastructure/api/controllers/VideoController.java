package com.dino.admin.catalogo.infrastructure.api.controllers;

import com.dino.admin.catalogo.application.video.create.CreateVideoCommand;
import com.dino.admin.catalogo.application.video.create.CreateVideoUseCase;
import com.dino.admin.catalogo.application.video.delete.DeleteVideoUseCase;
import com.dino.admin.catalogo.application.video.retrieve.get.GetVideoByIdUseCase;
import com.dino.admin.catalogo.application.video.retrieve.list.ListVideosUseCase;
import com.dino.admin.catalogo.application.video.update.UpdateVideoCommand;
import com.dino.admin.catalogo.application.video.update.UpdateVideoUseCase;
import com.dino.admin.catalogo.domain.castmember.CastMemberID;
import com.dino.admin.catalogo.domain.category.CategoryId;
import com.dino.admin.catalogo.domain.genre.GenreID;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.resource.Resource;
import com.dino.admin.catalogo.domain.utils.CollectionUtils;
import com.dino.admin.catalogo.domain.video.VideoSearchQuery;
import com.dino.admin.catalogo.infrastructure.api.VideoAPI;
import com.dino.admin.catalogo.infrastructure.utils.HashingUtils;
import com.dino.admin.catalogo.infrastructure.video.models.CreateVideoRequest;
import com.dino.admin.catalogo.infrastructure.video.models.UpdateVideoRequest;
import com.dino.admin.catalogo.infrastructure.video.models.VideoListResponse;
import com.dino.admin.catalogo.infrastructure.video.models.VideoResponse;
import com.dino.admin.catalogo.infrastructure.video.presenters.VideoApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Objects;
import java.util.Set;

@RestController
public class VideoController implements VideoAPI {

    private final CreateVideoUseCase createVideoUseCase;
    private final GetVideoByIdUseCase getVideoByIdUseCase;
    private final UpdateVideoUseCase updateVideoUseCase;
    private final DeleteVideoUseCase deleteVideoUseCase;
    private final ListVideosUseCase listVideosUseCase;

    public VideoController(
            final CreateVideoUseCase createVideoUseCase,
            final GetVideoByIdUseCase getVideoByIdUseCase,
            final UpdateVideoUseCase updateVideoUseCase,
            final DeleteVideoUseCase deleteVideoUseCase,
            final ListVideosUseCase listVideosUseCase
    ) {
        this.createVideoUseCase = Objects.requireNonNull(createVideoUseCase);
        this.getVideoByIdUseCase = Objects.requireNonNull(getVideoByIdUseCase);
        this.updateVideoUseCase = Objects.requireNonNull(updateVideoUseCase);
        this.deleteVideoUseCase = Objects.requireNonNull(deleteVideoUseCase);
        this.listVideosUseCase = Objects.requireNonNull(listVideosUseCase);

    }

    @Override
    public Pagination<VideoListResponse> list(
                      final String search,
                      final int page,
                      final int perPage,
                      final String sort,
                      final String dir,
                      final Set<String> castMembers,
                      final Set<String> categories,
                      final Set<String> genres
    ) {
        final var aQuery = new VideoSearchQuery(page, perPage, search, sort, dir,
                CollectionUtils.mapTo(castMembers, CastMemberID::from),
                CollectionUtils.mapTo(categories, CategoryId::from),
                CollectionUtils.mapTo(genres, GenreID::from)
        );
        return VideoApiPresenter.present(this.listVideosUseCase.execute(aQuery));
    }

    @Override
    public ResponseEntity<?> createFull(
                                        final String aTitle,
                                        final String aDescription,
                                        final Integer launchedAt,
                                        final Double aDuration,
                                        final Boolean wasOpened,
                                        final Boolean wasPublished,
                                        final String aRating,
                                        final Set<String> categories,
                                        final Set<String> castMembers,
                                        final Set<String> genres,
                                        final MultipartFile videoFile,
                                        final MultipartFile trailerFile,
                                        final MultipartFile bannerFile,
                                        final MultipartFile thumbFile,
                                        final MultipartFile thumbHalfFile
    ) {
        final var aCmd = CreateVideoCommand.with(
                aTitle,
                aDescription,
                launchedAt,
                aDuration,
                wasOpened,
                wasPublished,
                aRating,
                categories,
                genres,
                castMembers,
                resourceOf(videoFile),
                resourceOf(trailerFile),
                resourceOf(bannerFile),
                resourceOf(thumbFile),
                resourceOf(thumbHalfFile)

        );

        final var output = this.createVideoUseCase.execute(aCmd);
        return ResponseEntity.created(URI.create("/videos/" + output.id())).body(output);
    }

    @Override
    public ResponseEntity<?> createPartial(final CreateVideoRequest payload) {
        final var aCmd = CreateVideoCommand.with(
                payload.title(),
                payload.description(),
                payload.launchedAt(),
                payload.duration(),
                payload.opened(),
                payload.published(),
                payload.rating(),
                payload.categories(),
                payload.genres(),
                payload.castMembers()
        );

        final var output = this.createVideoUseCase.execute(aCmd);
        return ResponseEntity.created(URI.create("/videos/" + output.id())).body(output);
    }

    @Override
    public VideoResponse getById(final String id) {
        return VideoApiPresenter.present(getVideoByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> update(String id, UpdateVideoRequest payload) {
        final var aCmd = UpdateVideoCommand.with(
                id,
                payload.title(),
                payload.description(),
                payload.launchedAt(),
                payload.duration(),
                payload.opened(),
                payload.published(),
                payload.rating(),
                payload.categories(),
                payload.genres(),
                payload.castMembers()
        );
        final var output = this.updateVideoUseCase.execute(aCmd);
        return ResponseEntity.ok()
                .location(URI.create("/videos/" + output.id()))
                .body(VideoApiPresenter.present(output));
    }

    @Override
    public void deleteById(String id) {
        this.deleteVideoUseCase.execute(id);
    }

    private Resource resourceOf(final MultipartFile part) {
        if(part == null){
            return null;
        }

        try {
            return Resource.with(
                        part.getBytes(),
                        HashingUtils.checksum(part.getBytes()),
                        part.getContentType(),
                        part.getOriginalFilename()
            );

        }catch (Throwable t){
            throw new RuntimeException(t);

        }
    }
}
