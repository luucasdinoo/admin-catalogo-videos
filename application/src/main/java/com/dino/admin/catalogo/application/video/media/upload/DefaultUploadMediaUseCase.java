package com.dino.admin.catalogo.application.video.media.upload;

import com.dino.admin.catalogo.domain.exceptions.NotFoundException;
import com.dino.admin.catalogo.domain.video.MediaResourceGateway;
import com.dino.admin.catalogo.domain.video.Video;
import com.dino.admin.catalogo.domain.video.VideoGateway;
import com.dino.admin.catalogo.domain.video.VideoID;

import java.util.Objects;

public class DefaultUploadMediaUseCase extends UploadMediaUseCase{

    private final VideoGateway videoGateway;
    private final MediaResourceGateway mediaResourceGateway;

    public DefaultUploadMediaUseCase(
            final VideoGateway videoGateway,
            final MediaResourceGateway mediaResourceGateway
    ) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
    }

    @Override
    public UploadMediaOutput execute(final UploadMediaCommand aCommand) {
        final var anId = VideoID.from(aCommand.videoId());
        final var aResource = aCommand.videoResource();

        final var aVideo = this.videoGateway.findById(anId)
                .orElseThrow(() -> NotFoundException.with(Video.class, anId));

        switch (aResource.type()){
            case VIDEO -> aVideo.updateVideoMedia(mediaResourceGateway.storeAudioVideo(anId, aResource));
            case TRAILER -> aVideo.updateTrailerMedia(mediaResourceGateway.storeAudioVideo(anId, aResource));
            case BANNER -> aVideo.updateBannerMedia(mediaResourceGateway.storeImage(anId, aResource));
            case THUMBNAIL -> aVideo.updateThumbnailMedia(mediaResourceGateway.storeImage(anId, aResource));
            case THUMBNAIL_HALF -> aVideo.updateThumbnailHalfMedia(mediaResourceGateway.storeImage(anId, aResource));
        }

        return UploadMediaOutput.with(videoGateway.update(aVideo), aResource.type());
    }
}
