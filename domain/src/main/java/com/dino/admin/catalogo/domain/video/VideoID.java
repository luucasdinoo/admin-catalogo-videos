package com.dino.admin.catalogo.domain.video;

import com.dino.admin.catalogo.domain.Identifier;
import com.dino.admin.catalogo.domain.utils.IdUtils;

import java.util.Objects;
import java.util.UUID;

public class VideoID extends Identifier {

    private final String value;

    private VideoID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static VideoID unique(){
        return new VideoID(IdUtils.uuid());
    }

    public static VideoID from(final String anId) {
        return new VideoID(anId);
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
