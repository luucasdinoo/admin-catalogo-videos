package com.dino.admin.catalogo.application.video.media.get;

import com.dino.admin.catalogo.domain.resource.Resource;

public record MediaOutput(
        byte[] content,
        String contentType,
        String name
) {
    public static MediaOutput with(Resource aResource) {
        return new MediaOutput(
                aResource.content(),
                aResource.contentType(),
                aResource.name()
        );
    }
}
