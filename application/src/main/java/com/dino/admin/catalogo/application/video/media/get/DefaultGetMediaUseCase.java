package com.dino.admin.catalogo.application.video.media.get;

import com.dino.admin.catalogo.domain.exceptions.NotFoundException;
import com.dino.admin.catalogo.domain.resource.Resource;
import com.dino.admin.catalogo.domain.validation.Error;
import com.dino.admin.catalogo.domain.video.MediaResourceGateway;
import com.dino.admin.catalogo.domain.video.VideoID;
import com.dino.admin.catalogo.domain.video.VideoMediaType;

import java.util.Objects;

public class DefaultGetMediaUseCase extends GetMediaUseCase{

    private final MediaResourceGateway mediaResourceGateway;

    public DefaultGetMediaUseCase(final MediaResourceGateway mediaResourceGateway) {
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
    }

    @Override
    public MediaOutput execute(GetMediaCommand aCommand) {
        VideoID anId = VideoID.from(aCommand.videoId());
        VideoMediaType aType = VideoMediaType.of(aCommand.mediaType())
                .orElseThrow(() -> NotFoundException.with(new Error("Invalid media type %s".formatted(aCommand.mediaType()))));

        Resource aResource = this.mediaResourceGateway.getResource(anId, aType)
                .orElseThrow(() -> NotFoundException.with(
                        new Error("Resource %s not found for video %s".formatted(aType.name(), anId.getValue()))));

        return MediaOutput.with(aResource);
    }
}
