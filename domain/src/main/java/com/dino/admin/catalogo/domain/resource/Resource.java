package com.dino.admin.catalogo.domain.resource;

import com.dino.admin.catalogo.domain.ValueObject;

import java.util.Objects;

public class Resource extends ValueObject {

    private final byte[] content;
    private final String checksum;
    private final String contentType;
    private final String name;

    private Resource(
            final byte[] content,
            final String checksum,
            final String contentType,
            final String name
    ) {
        this.checksum = Objects.requireNonNull(checksum);
        this.content = Objects.requireNonNull(content);
        this.contentType = Objects.requireNonNull(contentType);
        this.name = Objects.requireNonNull(name);
    }

    public static Resource with(
            final byte[] content,
            final String checksum,
            final String contentType,
            final String name
    ){
        return new Resource(content, checksum , contentType, name);
    }

    public byte[] content() {
        return content;
    }

    public String contentType() {
        return contentType;
    }

    public String name() {
        return name;
    }

    public String checksum(){
        return checksum;
    }


}
