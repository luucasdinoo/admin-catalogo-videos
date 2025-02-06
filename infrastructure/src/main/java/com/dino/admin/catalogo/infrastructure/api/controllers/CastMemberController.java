package com.dino.admin.catalogo.infrastructure.api.controllers;

import com.dino.admin.catalogo.application.castmember.create.CreateCastMemberCommand;
import com.dino.admin.catalogo.application.castmember.create.CreateCastMemberUseCase;
import com.dino.admin.catalogo.application.castmember.delete.DeleteCastMemberUseCase;
import com.dino.admin.catalogo.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import com.dino.admin.catalogo.application.castmember.retrieve.list.ListCastMemberUseCase;
import com.dino.admin.catalogo.application.castmember.update.UpdateCastMemberCommand;
import com.dino.admin.catalogo.application.castmember.update.UpdateCastMemberUseCase;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.pagination.SearchQuery;
import com.dino.admin.catalogo.infrastructure.api.CastMemberAPI;
import com.dino.admin.catalogo.infrastructure.castmember.models.CastMemberListResponse;
import com.dino.admin.catalogo.infrastructure.castmember.models.CastMemberResponse;
import com.dino.admin.catalogo.infrastructure.castmember.models.CreateCastMemberRequest;
import com.dino.admin.catalogo.infrastructure.castmember.models.UpdateCastMemberRequest;
import com.dino.admin.catalogo.infrastructure.castmember.presenters.CastMemberPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class CastMemberController implements CastMemberAPI {

    private final CreateCastMemberUseCase createCastMemberUseCase;
    private final UpdateCastMemberUseCase updateCastMemberUseCase;
    private final DeleteCastMemberUseCase deleteCastMemberUseCase;
    private final ListCastMemberUseCase listCastMemberUseCase;
    private final GetCastMemberByIdUseCase getGenreByIdUseCase;

    public CastMemberController(
            final CreateCastMemberUseCase createCastMemberUseCase,
            final UpdateCastMemberUseCase updateCastMemberUseCase,
            final DeleteCastMemberUseCase deleteCastMemberUseCase,
            final ListCastMemberUseCase listCastMemberUseCase,
            final GetCastMemberByIdUseCase getGenreByIdUseCase
    ) {
        this.createCastMemberUseCase = Objects.requireNonNull(createCastMemberUseCase);
        this.updateCastMemberUseCase = Objects.requireNonNull(updateCastMemberUseCase);
        this.deleteCastMemberUseCase = Objects.requireNonNull(deleteCastMemberUseCase);
        this.listCastMemberUseCase = Objects.requireNonNull(listCastMemberUseCase);
        this.getGenreByIdUseCase = Objects.requireNonNull(getGenreByIdUseCase);
    }

    @Override
    public ResponseEntity<?> create(final CreateCastMemberRequest input) {
        final var aCommand =
                CreateCastMemberCommand.with(input.name(), input.type());

        final var output = this.createCastMemberUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/cast_members" + output.id())).body(output);
    }

    @Override
    public Pagination<CastMemberListResponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String dir
    ) {
        return this.listCastMemberUseCase.execute(new SearchQuery(page, perPage, sort, sort, dir))
                .map(CastMemberPresenter::present);
    }

    @Override
    public CastMemberResponse getById(final String id) {
        return CastMemberPresenter.present(this.getGenreByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCastMemberRequest request) {
        final var aCommand =
                UpdateCastMemberCommand.with(id, request.name(), request.type());

        final var output = this.updateCastMemberUseCase.execute(aCommand);

        return ResponseEntity.ok(output);
    }

    @Override
    public void deleteById(final String id) {
        this.deleteCastMemberUseCase.execute(id);
    }
}
