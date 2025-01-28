package com.dino.admin.catalogo.application.category.create;

import com.dino.admin.catalogo.application.UseCase;
import com.dino.admin.catalogo.domain.category.Category;
import com.dino.admin.catalogo.domain.category.CategoryGateway;
import com.dino.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends UseCase<CreateCategoryRequest, CreateCategoryResponse> {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CreateCategoryResponse execute(final CreateCategoryRequest aRequest) {
        final Category aCategory = Category.newCategory(aRequest.name(), aRequest.description(), aRequest.isActive());
        aCategory.validate(new ThrowsValidationHandler());
        return CreateCategoryResponse.from(this.categoryGateway.create(aCategory));
    }
}
