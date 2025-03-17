package com.dino.admin.catalogo.infrastructure.video.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record CreateVideoRequest(
        @JsonProperty("title") String title,
        @JsonProperty("description") String description,
        @JsonProperty("duration") Double duration,
        @JsonProperty("year_launched") Integer launchedAt,
        @JsonProperty("opened") Boolean opened,
        @JsonProperty("published") Boolean published,
        @JsonProperty("rating") String rating,
        @JsonProperty("categories") Set<String> categories,
        @JsonProperty("categories") Set<String> genres,
        @JsonProperty("cast_members") Set<String> castMembers
) {
}
