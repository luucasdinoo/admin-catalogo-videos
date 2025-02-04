package com.dino.admin.catalogo.infrastructure.api.controllers;

import com.dino.admin.catalogo.application.category.create.CreateCategoryRequest;
import com.dino.admin.catalogo.application.category.create.CreateCategoryResponse;
import com.dino.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.dino.admin.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.validation.handler.Notification;
import com.dino.admin.catalogo.infrastructure.api.CategoryAPI;
import com.dino.admin.catalogo.infrastructure.category.models.CategoryApiOutput;
import com.dino.admin.catalogo.infrastructure.category.models.CreateCategoryApiInput;
import com.dino.admin.catalogo.infrastructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase, GetCategoryByIdUseCase getCategoryByIdUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryApiInput input) {
        final var aCommand = CreateCategoryRequest.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        final Function<CreateCategoryResponse, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, String sort, String dir) {
        return null;
    }

    @Override
    public CategoryApiOutput getById(String id) {
        return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(id));
    }
}
