package com.dino.admin.catalogo.domain.genre;

import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface GenreGateway {

    Genre create(Genre aGenre);

    void deleteById(GenreID anId);

    Pagination<Genre> findAll(SearchQuery aQuery);

    Optional<Genre> findById(GenreID anId);

    Genre update(Genre aGenre);

    List<GenreID> existsByIds(Iterable<GenreID> ids);

}
