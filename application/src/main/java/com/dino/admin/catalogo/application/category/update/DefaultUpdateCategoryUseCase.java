package com.dino.admin.catalogo.application.category.update;

import com.dino.admin.catalogo.domain.category.Category;
import com.dino.admin.catalogo.domain.category.CategoryGateway;
import com.dino.admin.catalogo.domain.category.CategoryId;
import com.dino.admin.catalogo.domain.exceptions.DomainException;
import com.dino.admin.catalogo.domain.validation.Error;
import com.dino.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {
    
    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryResponse> execute(final UpdateCategoryRequest aRequest) {
        final var anId = CategoryId.from(aRequest.id());

       final var aCategory = this.categoryGateway.findById(anId)
                .orElseThrow(() -> DomainException.with(
                        new Error("Category with ID %s was not found".formatted(anId.getValue()))));

       final var notification = Notification.create();
       aCategory.update(aRequest.name(), aRequest.description(), aRequest.isActive()).validate(notification);

       return notification.hasError() ? API.Left(notification): update(aCategory);
    }

    private Either<Notification, UpdateCategoryResponse> update(Category aCategory) {
        return API.Try(() -> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryResponse::from);
    }
}
