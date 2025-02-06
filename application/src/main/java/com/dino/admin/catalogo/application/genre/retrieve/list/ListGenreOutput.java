package com.dino.admin.catalogo.application.genre.retrieve.list;

import com.dino.admin.catalogo.domain.category.CategoryId;
import com.dino.admin.catalogo.domain.genre.Genre;

import java.time.Instant;
import java.util.List;

public record ListGenreOutput(
        String id,
        String name,
        boolean isActive,
        List<String> categories,
        Instant createdAt,
        Instant deletedAt
 ) {

    public static ListGenreOutput from(final Genre aGenre){
        return new ListGenreOutput(
                aGenre.getId().getValue(),
                aGenre.getName(),
                aGenre.isActive(),
                aGenre.getCategories().stream().map(CategoryId::getValue).toList(),
                aGenre.getCreatedAt(),
                aGenre.getDeletedAt()
        );
    }
}
