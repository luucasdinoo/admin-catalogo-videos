package com.dino.admin.catalogo.application.video.media.upload;

import com.dino.admin.catalogo.domain.video.Video;
import com.dino.admin.catalogo.domain.video.VideoMediaType;

public record UploadMediaOutput(
        String videoId,
        VideoMediaType mediaType
) {
    public static UploadMediaOutput with(Video aVideo, VideoMediaType aType){
        return new UploadMediaOutput(aVideo.getId().getValue(), aType);
    }
}
