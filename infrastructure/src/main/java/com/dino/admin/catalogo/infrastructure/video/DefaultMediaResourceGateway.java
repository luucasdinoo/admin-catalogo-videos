package com.dino.admin.catalogo.infrastructure.video;

import com.dino.admin.catalogo.domain.resource.Resource;
import com.dino.admin.catalogo.domain.video.*;
import com.dino.admin.catalogo.infrastructure.config.properties.storage.StorageProperties;
import com.dino.admin.catalogo.infrastructure.services.StorageService;
import org.springframework.stereotype.Component;

@Component
public class DefaultMediaResourceGateway implements MediaResourceGateway {

    private final String filenamePattern;
    private final String locationPattern;
    private final StorageService storageService;

    public DefaultMediaResourceGateway(final StorageProperties props, final StorageService storageService) {
        this.filenamePattern = props.getFileNamePattern();
        this.locationPattern = props.getLocationPattern();
        this.storageService = storageService;
    }

    //TODO -> dois ultimos parâmetros null
    @Override
    public AudioVideoMedia storeAudioVideo(final VideoID anId, final VideoResource videoResource) {
        final var filepath = filepath(anId, videoResource);
        final var aResource = videoResource.resource();
        store(filepath, aResource);
        return AudioVideoMedia.with(aResource.checksum(), aResource.name(), filepath, null, null);
    }

    @Override
    public ImageMedia storeImage(final VideoID anId, final VideoResource imageResource) {
        final var filepath = filepath(anId, imageResource);
        final var aResource = imageResource.resource();
        store(filepath, aResource);
        return ImageMedia.with(aResource.checksum(), aResource.name(), filepath);
    }

    @Override
    public void clearResources(final VideoID andId) {
        final var ids = this.storageService.list(folder(andId));
        this.storageService.deleteAll(ids);
    }

    private String filename(final VideoMediaType aType) {
        return filenamePattern.replace("{type}", aType.toString());
    }

    private String folder(final VideoID anId) {
        return locationPattern.replace("{videoId}", anId.getValue());
    }

    private String filepath(final VideoID anId, final VideoResource aResource) {
        return folder(anId)
                .concat("/")
                .concat(filename(aResource.type()));
    }

    private void store(final String filepath, final Resource aResource){
        this.storageService.store(filepath, aResource);
    }
}
