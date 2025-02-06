package com.dino.admin.catalogo.infrastructure.api.controllers;

import com.dino.admin.catalogo.application.genre.create.CreateGenreCommand;
import com.dino.admin.catalogo.application.genre.create.CreateGenreUseCase;
import com.dino.admin.catalogo.application.genre.delete.DeleteGenreUseCase;
import com.dino.admin.catalogo.application.genre.retrieve.get.GetGenreByIdUseCase;
import com.dino.admin.catalogo.application.genre.retrieve.list.ListGenreUseCase;
import com.dino.admin.catalogo.application.genre.update.UpdateGenreCommand;
import com.dino.admin.catalogo.application.genre.update.UpdateGenreUseCase;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.pagination.SearchQuery;
import com.dino.admin.catalogo.infrastructure.api.GenreAPI;
import com.dino.admin.catalogo.infrastructure.genre.models.CreateGenreRequest;
import com.dino.admin.catalogo.infrastructure.genre.models.GenreListResponse;
import com.dino.admin.catalogo.infrastructure.genre.models.GenreResponse;
import com.dino.admin.catalogo.infrastructure.genre.models.UpdateGenreRequest;
import com.dino.admin.catalogo.infrastructure.genre.presenters.GenreApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class GenreController implements GenreAPI {

    private final CreateGenreUseCase createGenreUseCase;
    private final ListGenreUseCase listGenreUseCase;
    private final GetGenreByIdUseCase getGenreByIdUseCase;
    private final UpdateGenreUseCase updateGenreUseCase;
    private final DeleteGenreUseCase deleteGenreUseCase;

    public GenreController(
            final CreateGenreUseCase createGenreUseCase,
            final ListGenreUseCase listGenreUseCase,
            final GetGenreByIdUseCase getGenreByIdUseCase,
            final UpdateGenreUseCase updateGenreUseCase,
            final DeleteGenreUseCase deleteGenreUseCase
    ) {
        this.createGenreUseCase = Objects.requireNonNull(createGenreUseCase);
        this.listGenreUseCase = Objects.requireNonNull(listGenreUseCase);
        this.getGenreByIdUseCase = Objects.requireNonNull(getGenreByIdUseCase);
        this.updateGenreUseCase = Objects.requireNonNull(updateGenreUseCase);
        this.deleteGenreUseCase = Objects.requireNonNull(deleteGenreUseCase);
    }

    @Override
    public ResponseEntity<?> create(final CreateGenreRequest input) {
        final var aCommand = CreateGenreCommand.with(
                input.name(),
                input.active(),
                input.categories()
        );

        final var output = this.createGenreUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/genres/" + output.id())).body(output);
    }

    @Override
    public Pagination<GenreListResponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String dir
    ) {
        return this.listGenreUseCase.execute(new SearchQuery(page, perPage, search, sort, dir))
                .map(GenreApiPresenter::present);
    }

    @Override
    public GenreResponse getById(final String id) {
        return GenreApiPresenter.present(this.getGenreByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateGenreRequest input) {
        final var aCommand = UpdateGenreCommand.with(
                id,
                input.name(),
                input.isActive(),
                input.categories()
        );

        final var output = this.updateGenreUseCase.execute(aCommand);

        return ResponseEntity.ok(output);
    }

    @Override
    public void deleteById(final String id) {
        this.deleteGenreUseCase.execute(id);
    }
}
