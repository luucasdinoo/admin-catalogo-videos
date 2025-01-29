package com.dino.admin.catalogo.application.category.retrieve.get;

import com.dino.admin.catalogo.domain.category.CategoryGateway;
import com.dino.admin.catalogo.domain.category.CategoryId;
import com.dino.admin.catalogo.domain.exceptions.DomainException;
import com.dino.admin.catalogo.domain.validation.Error;

import java.util.Objects;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryResponse execute(final String anIn) {
        return this.categoryGateway.findById(CategoryId.from(anIn))
                .map(CategoryResponse::from)
                .orElseThrow(() -> DomainException.with(new Error("Category with ID %s was not found".formatted(anIn))));
    }
}
