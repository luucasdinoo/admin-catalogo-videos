package com.dino.admin.catalogo.domain.video;

import com.dino.admin.catalogo.domain.resource.Resource;

import java.util.Optional;

public interface MediaResourceGateway {

    AudioVideoMedia storeAudioVideo(VideoID andId, VideoResource aResource);

    ImageMedia storeImage(VideoID andId, VideoResource aResource);

    void clearResources(VideoID andId);

    Optional<Resource> getResource(VideoID anId, VideoMediaType aType);
}
