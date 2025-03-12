package com.dino.admin.catalogo.application.video.retrieve.list;

import com.dino.admin.catalogo.domain.video.VideoPreview;

import java.time.Instant;

public record VideoListOutput(
        String id,
        String title,
        String description,
        Instant createdAt,
        Instant updatedAt
) {

    //TODO -> Antes recebia Video mas dava erro.
    public static VideoListOutput from(final VideoPreview aVideo){
        return new VideoListOutput(
                aVideo.id(),
                aVideo.title(),
                aVideo.description(),
                aVideo.createdAt(),
                aVideo.updatedAt()
        );
    }
}
