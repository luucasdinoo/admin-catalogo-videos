package com.dino.admin.catalogo.application.genre.retrieve.list;

import com.dino.admin.catalogo.domain.genre.GenreGateway;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListGenreUseCase extends ListGenreUseCase{

    private final GenreGateway genreGateway;

    public DefaultListGenreUseCase(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public Pagination<ListGenreOutput> execute(SearchQuery aQuery) {
        return this.genreGateway.findAll(aQuery)
                .map(ListGenreOutput::from);
    }
}
