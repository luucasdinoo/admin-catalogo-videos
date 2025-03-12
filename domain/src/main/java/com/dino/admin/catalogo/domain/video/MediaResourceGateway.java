package com.dino.admin.catalogo.domain.video;

public interface MediaResourceGateway {

    AudioVideoMedia storeAudioVideo(VideoID andId, VideoResource aResource);

    ImageMedia storeImage(VideoID andId, VideoResource aResource);

    void clearResources(VideoID andId);
}
