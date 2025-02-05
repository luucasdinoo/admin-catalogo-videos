package com.dino.admin.catalogo.application.genre.update;

import com.dino.admin.catalogo.domain.category.CategoryGateway;
import com.dino.admin.catalogo.domain.category.CategoryId;
import com.dino.admin.catalogo.domain.exceptions.NotFoundException;
import com.dino.admin.catalogo.domain.exceptions.NotificationException;
import com.dino.admin.catalogo.domain.genre.Genre;
import com.dino.admin.catalogo.domain.genre.GenreGateway;
import com.dino.admin.catalogo.domain.genre.GenreID;
import com.dino.admin.catalogo.domain.validation.Error;
import com.dino.admin.catalogo.domain.validation.ValidationHandler;
import com.dino.admin.catalogo.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultUpdateGenreUseCase extends UpdateGenreUseCase{

    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;

    public DefaultUpdateGenreUseCase(
            final CategoryGateway categoryGateway,
            final GenreGateway genreGateway
    ) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public UpdateGenreOutput execute(final UpdateGenreCommand aCommand) {
        final var categories = toCategoryId(aCommand.categories());

        Genre aGenre = this.genreGateway.findById(GenreID.from(aCommand.id()))
                .orElseThrow(() -> NotFoundException.with(Genre.class, GenreID.from(aCommand.id())));

        final var notification = Notification.create();
        notification.append(validateCategories(categories));
        notification.validate(() -> aGenre.update(aCommand.name(), aCommand.isActive(), categories));

        if (notification.hasError()){
            throw new NotificationException("Could not update Aggregate Genre %s".formatted(aCommand.id()), notification);
        }

        return UpdateGenreOutput.from(this.genreGateway.update(aGenre));
    }

    private ValidationHandler validateCategories(final List<CategoryId> ids) {
        final var notification = Notification.create();
        if (ids == null || ids.isEmpty()) {
            return notification;
        }

        final var retrievedIds = categoryGateway.existsByIds(ids);

        if (ids.size() != retrievedIds.size()) {
            final var missingIds = new ArrayList<>(ids);
            missingIds.removeAll(retrievedIds);

            final var missingIdsMessage = missingIds.stream()
                    .map(CategoryId::getValue)
                    .collect(Collectors.joining(", "));

            notification.append(new Error("Some categories could not be found: %s".formatted(missingIdsMessage)));
        }

        return notification;
    }

    private List<CategoryId> toCategoryId(final List<String> categories) {
        return categories.stream()
                .map(CategoryId::from)
                .toList();
    }
}
