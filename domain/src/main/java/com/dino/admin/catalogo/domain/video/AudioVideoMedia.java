package com.dino.admin.catalogo.domain.video;

import com.dino.admin.catalogo.domain.ValueObject;

import java.util.Objects;

public class AudioVideoMedia extends ValueObject {

    private final String checkSum;
    private final String name;
    private final String rawLocation;
    private final String encodedLocation;
    private final MediaStatus status;

    private AudioVideoMedia(
            final String checkSum,
            final String name,
            final String rawLocation,
            final String encodedLocation,
            final MediaStatus status
    ) {
        this.checkSum = Objects.requireNonNull(checkSum);
        this.name = Objects.requireNonNull(name);
        this.rawLocation = Objects.requireNonNull(rawLocation);
        this.encodedLocation = Objects.requireNonNull(encodedLocation);
        this.status = Objects.requireNonNull(status);
    }

    public static AudioVideoMedia with(
            final String checkSum,
            final String name,
            final String rawLocation,
            final String encodedLocation,
            final MediaStatus status
    ){
        return new AudioVideoMedia(checkSum, name, rawLocation, encodedLocation, status);
    }

    public String checkSum() {
        return checkSum;
    }

    public String name() {
        return name;
    }

    public String rawLocation() {
        return rawLocation;
    }

    public String encodedLocation() {
        return encodedLocation;
    }

    public MediaStatus status() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AudioVideoMedia that = (AudioVideoMedia) o;
        return Objects.equals(checkSum, that.checkSum) && Objects.equals(rawLocation, that.rawLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkSum, rawLocation);
    }
}
