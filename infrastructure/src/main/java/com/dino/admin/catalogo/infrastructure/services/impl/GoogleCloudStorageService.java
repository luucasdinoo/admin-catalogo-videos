package com.dino.admin.catalogo.infrastructure.services.impl;

import com.dino.admin.catalogo.domain.resource.Resource;
import com.dino.admin.catalogo.infrastructure.services.StorageService;
import com.google.api.client.util.Value;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class GoogleCloudStorageService implements StorageService {

    @Value("google.cloud.storage.catalogo-videos.bucket")
    private String bucket;

    private final Storage storage;

    public GoogleCloudStorageService(final Storage storage) {

        this.storage = storage;
    }

    @Override
    public void deleteAll(final Collection<String> names) {
        final var blobs = names.stream()
                .map(name -> BlobId.of(this.bucket, name))
                .toList();

        this.storage.delete(blobs);
    }

    @Override
    public Optional<Resource> get(final String name) {
        return Optional.ofNullable(this.storage.get(this.bucket, name))
                .map(blob -> Resource.with(blob.getContent(), blob.getCrc32cToHexString(), blob.getContentType(), name));
    }

    @Override
    public List<String> list(final String prefix) {
        final var blobs = this.storage.list(this.bucket, Storage.BlobListOption.prefix(prefix));

        return StreamSupport.stream(blobs.iterateAll().spliterator(), false)
                .map(BlobInfo::getBlobId)
                .map(BlobId::getName)
                .toList();
    }

    @Override
    public void store(final String name, Resource resource) {
        final var blobInfo = BlobInfo.newBuilder(this.bucket, name)
                .setContentType(resource.contentType())
                .setCrc32cFromHexString("")
                .build();

        this.storage.create(blobInfo, resource.content());
    }
}
