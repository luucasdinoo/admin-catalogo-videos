package com.dino.admin.catalogo.application.video.create;

import com.dino.admin.catalogo.domain.video.Video;

public record CreateVideoOutput(String id) {

    public static CreateVideoOutput from(final Video aVideo){
        return new CreateVideoOutput(aVideo.getId().getValue());
    }
}
