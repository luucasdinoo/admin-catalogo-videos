package com.dino.admin.catalogo.infrastructure.api.controllers;

import com.dino.admin.catalogo.application.category.create.CreateCategoryRequest;
import com.dino.admin.catalogo.application.category.create.CreateCategoryResponse;
import com.dino.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.dino.admin.catalogo.application.category.delete.DeleteCategoryUseCase;
import com.dino.admin.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.dino.admin.catalogo.application.category.retrieve.list.ListCategoryUseCase;
import com.dino.admin.catalogo.application.category.update.UpdateCategoryRequest;
import com.dino.admin.catalogo.application.category.update.UpdateCategoryResponse;
import com.dino.admin.catalogo.application.category.update.UpdateCategoryUseCase;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.pagination.SearchQuery;
import com.dino.admin.catalogo.domain.validation.handler.Notification;
import com.dino.admin.catalogo.infrastructure.api.CategoryAPI;
import com.dino.admin.catalogo.infrastructure.category.models.CategoryListResponse;
import com.dino.admin.catalogo.infrastructure.category.models.CreateCategoryApiInput;
import com.dino.admin.catalogo.infrastructure.category.models.UpdateCategoryApiInput;
import com.dino.admin.catalogo.infrastructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final ListCategoryUseCase listCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase,
                              final ListCategoryUseCase listCategoryUseCase,
                              final GetCategoryByIdUseCase getCategoryByIdUseCase,
                              final UpdateCategoryUseCase updateCategoryUseCase,
                              final DeleteCategoryUseCase deleteCategoryUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.listCategoryUseCase = Objects.requireNonNull(listCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
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
    public Pagination<?> listCategories(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String dir
    ) {
        return listCategoryUseCase
                .execute(new SearchQuery(page, perPage, search, sort, dir));
    }

    @Override
    public CategoryListResponse getById(String id) {
        return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryApiInput input) {
        final var aCommand = UpdateCategoryRequest.with(
                id,
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryResponse, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(String anId) {
        this.deleteCategoryUseCase.execute(anId);
    }
}
