package com.dino.admin.catalogo.application.category.retrieve.get;

import com.dino.admin.catalogo.domain.category.Category;
import com.dino.admin.catalogo.domain.category.CategoryGateway;
import com.dino.admin.catalogo.domain.category.CategoryId;
import com.dino.admin.catalogo.domain.exceptions.NotFoundException;

import java.util.Objects;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryResponse execute(final String anID) {
        return this.categoryGateway.findById(CategoryId.from(anID))
                .map(CategoryResponse::from)
                .orElseThrow(() -> NotFoundException.with(Category.class, CategoryId.from(anID)));
    }
}
