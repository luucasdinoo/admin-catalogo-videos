package com.dino.admin.catalogo.application.genre.retrieve.get;

import com.dino.admin.catalogo.domain.exceptions.NotFoundException;
import com.dino.admin.catalogo.domain.genre.Genre;
import com.dino.admin.catalogo.domain.genre.GenreGateway;
import com.dino.admin.catalogo.domain.genre.GenreID;

import java.util.Objects;

public class DefaultGetGenreByIdUseCase extends GetGenreByIdUseCase {

    private final GenreGateway genreGateway;

    public DefaultGetGenreByIdUseCase(GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public GenreOutput execute(String anId) {
        return this.genreGateway.findById(GenreID.from(anId))
                .map(GenreOutput::from)
                .orElseThrow(() -> NotFoundException.with(Genre.class, GenreID.from(anId)));
    }
}
