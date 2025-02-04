package com.dino.admin.catalogo.infrastructure.category.presenters;

import com.dino.admin.catalogo.application.category.retrieve.get.CategoryResponse;
import com.dino.admin.catalogo.infrastructure.category.models.CategoryApiOutput;

import java.util.function.Function;

public interface CategoryApiPresenter {

    Function<CategoryResponse, CategoryApiOutput> present =
            output -> new CategoryApiOutput(
            output.id().getValue(),
            output.name(),
            output.description(),
            output.isActive(),
            output.createdAt(),
            output.updatedAt(),
            output.deletedAt()
    );

    static CategoryApiOutput present(final CategoryResponse output){
        return new CategoryApiOutput(
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
