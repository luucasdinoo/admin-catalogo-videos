package com.dino.admin.catalogo.domain.video;

public interface MediaResourceGateway {

    AudioVideoMedia storeAudioVideo(VideoID andId, Resource aResource);

    ImageMedia storeImage(VideoID andId, Resource aResource);

    void clearResources(VideoID andId);
}
