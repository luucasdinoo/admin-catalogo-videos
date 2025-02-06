package com.dino.admin.catalogo.infrastructure.category.presenters;

import com.dino.admin.catalogo.application.category.retrieve.get.CategoryResponse;
import com.dino.admin.catalogo.infrastructure.category.models.CategoryListResponse;

import java.util.function.Function;

public interface CategoryApiPresenter {

    Function<CategoryResponse, CategoryListResponse> present =
            output -> new CategoryListResponse(
            output.id().getValue(),
            output.name(),
            output.description(),
            output.isActive(),
            output.createdAt(),
            output.updatedAt(),
            output.deletedAt()
    );

    static CategoryListResponse present(final CategoryResponse output){
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}
