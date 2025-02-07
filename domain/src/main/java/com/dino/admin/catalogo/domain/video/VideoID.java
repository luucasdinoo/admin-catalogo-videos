package com.dino.admin.catalogo.domain.video;

import com.dino.admin.catalogo.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class VideoID extends Identifier {

    private final String value;

    private VideoID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static VideoID unique(){
        return new VideoID(UUID.randomUUID().toString().toLowerCase());
    }

    public static VideoID from(final String anId) {
        return new VideoID(anId);
    }

    public static VideoID from(final UUID anId) {
        return new VideoID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VideoID videoID = (VideoID) o;
        return Objects.equals(getValue(), videoID.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
