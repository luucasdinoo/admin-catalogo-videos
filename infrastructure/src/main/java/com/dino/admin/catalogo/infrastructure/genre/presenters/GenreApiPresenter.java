package com.dino.admin.catalogo.infrastructure.genre.presenters;

import com.dino.admin.catalogo.application.genre.retrieve.get.GenreOutput;
import com.dino.admin.catalogo.application.genre.retrieve.list.ListGenreOutput;
import com.dino.admin.catalogo.infrastructure.genre.models.GenreListResponse;
import com.dino.admin.catalogo.infrastructure.genre.models.GenreResponse;

public interface GenreApiPresenter {

    static GenreResponse present(final GenreOutput output){
        return new GenreResponse(
                output.id(),
                output.name(),
                output.categories(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static GenreListResponse present(final ListGenreOutput output){
        return new GenreListResponse(
                output.id(),
                output.name(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
