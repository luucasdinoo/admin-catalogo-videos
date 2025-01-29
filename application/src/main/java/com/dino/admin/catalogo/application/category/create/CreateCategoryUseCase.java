package com.dino.admin.catalogo.application.category.create;

import com.dino.admin.catalogo.application.UseCase;
import com.dino.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase
        extends UseCase<CreateCategoryRequest, Either<Notification, CreateCategoryResponse>> {
}
