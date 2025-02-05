package com.dino.admin.catalogo.application.genre.retrieve.list;

import com.dino.admin.catalogo.application.UseCase;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.pagination.SearchQuery;

public abstract class ListGenreUseCase
        extends UseCase<SearchQuery, Pagination<ListGenreOutput>> {
}
