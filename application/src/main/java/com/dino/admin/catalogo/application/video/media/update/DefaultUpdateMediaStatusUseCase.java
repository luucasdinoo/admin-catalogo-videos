package com.dino.admin.catalogo.application.video.media.update;

import com.dino.admin.catalogo.domain.exceptions.NotFoundException;
import com.dino.admin.catalogo.domain.video.*;

import java.util.Objects;

public class DefaultUpdateMediaStatusUseCase extends UpdateMediaStatusUseCase{

    private final VideoGateway videoGateway;

    public DefaultUpdateMediaStatusUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public void execute(final UpdateMediaStatusCommand aCommand) {
        final VideoID anId = VideoID.from(aCommand.videoId());
        final String aResourceId = aCommand.resourceId();

        final Video aVideo = this.videoGateway.findById(anId)
                .orElseThrow(() -> NotFoundException.with(Video.class, anId));

        final String encodedPath = "%s/%s".formatted(aCommand.folder(), aCommand.filename());

        if (matches(aResourceId, aVideo.getVideo().orElse(null))){
            updateVideo(VideoMediaType.VIDEO, aCommand.status(), aVideo, encodedPath);

        } else if (matches(aResourceId, aVideo.getTrailer().orElse(null))){
            updateVideo(VideoMediaType.TRAILER, aCommand.status(), aVideo, encodedPath);

        }
    }

    private void updateVideo(final VideoMediaType aType, final MediaStatus status, final Video aVideo, final String encodedPath) {
        switch (status){
            case PENDING -> {}
            case PROCESSING -> aVideo.processing(aType);
            case COMPLETED -> aVideo.completed(aType, encodedPath);

        }
        this.videoGateway.update(aVideo);
    }

    private boolean matches(final String anId, final AudioVideoMedia aMedia){
        if (aMedia == null) {
            return false;
        }

        return aMedia.id().equals(anId);
    }
}
