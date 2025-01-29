package com.dino.admin.catalogo.application.category.update;

import com.dino.admin.catalogo.application.UseCase;
import com.dino.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
        extends UseCase<UpdateCategoryRequest, Either<Notification, UpdateCategoryResponse>> {}
