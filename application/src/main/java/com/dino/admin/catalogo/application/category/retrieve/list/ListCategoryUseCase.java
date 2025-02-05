package com.dino.admin.catalogo.application.category.retrieve.list;

import com.dino.admin.catalogo.application.UseCase;
import com.dino.admin.catalogo.domain.pagination.SearchQuery;
import com.dino.admin.catalogo.domain.pagination.Pagination;

public abstract class ListCategoryUseCase extends UseCase<SearchQuery, Pagination<CategoryListResponse>> {
}
