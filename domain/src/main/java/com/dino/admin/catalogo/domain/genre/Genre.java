package com.dino.admin.catalogo.domain.genre;

import com.dino.admin.catalogo.domain.AggregateRoot;
import com.dino.admin.catalogo.domain.category.CategoryId;
import com.dino.admin.catalogo.domain.exceptions.NotificationException;
import com.dino.admin.catalogo.domain.utils.InstantUtils;
import com.dino.admin.catalogo.domain.validation.ValidationHandler;
import com.dino.admin.catalogo.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Genre extends AggregateRoot<GenreID> implements Cloneable{

    private String name;
    private boolean active;
    private List<CategoryId> categories;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;


    protected Genre(
            final GenreID anId,
            final String aName,
            final boolean isActive,
            final List<CategoryId> aCategories,
            final Instant aCreatedAt,
            final Instant aUpdatedAt,
            final Instant aDeletedAt
    ) {
        super(anId);
        this.name = aName;
        this.active = isActive;
        this.categories = aCategories;
        this.createdAt = aCreatedAt;
        this.updatedAt = aUpdatedAt;
        this.deletedAt = aDeletedAt;

        selfValidate();
    }

    public static Genre newGenre(final String aName, final boolean isActive){
        GenreID anId = GenreID.unique();
        Instant now = InstantUtils.now();
        Instant deletedAt = isActive ? null : now;
        return new Genre(anId, aName, isActive,new ArrayList<>(), now, now, deletedAt);
    }

    public static Genre with(
                final GenreID anId,
                final String aName,
                final boolean isActive,
                final List<CategoryId> aCategories,
                final Instant aCreatedAt,
                final Instant aUpdatedAt,
                final Instant aDeletedAt
    ) {
        return new Genre(anId, aName, isActive, aCategories, aCreatedAt, aUpdatedAt, aDeletedAt);
    }

    public static Genre with(final Genre aGenre){
        return new Genre(
                aGenre.id,
                aGenre.name,
                aGenre.active,
                new ArrayList<>(aGenre.categories),
                aGenre.createdAt,
                aGenre.updatedAt,
                aGenre.deletedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new GenreValidator(this, handler).validate();
    }

    public Genre update(final String aName, final boolean isActive, final List<CategoryId> aCategories){
        if (isActive){
            activate();
        } else {
            deactivate();
        }
        this.name = aName;
        this.categories = new ArrayList<>(aCategories);
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    public void activate(){
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = InstantUtils.now();
    }

    public void deactivate(){
        if (getDeletedAt() == null){
            this.deletedAt = InstantUtils.now();
        }
        this.active = false;
        this.updatedAt = InstantUtils.now();
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public List<CategoryId> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public Genre clone() {
        try {
            Genre clone = (Genre) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()){
            throw new NotificationException("Failed to create a Aggregate Genre", notification);
        }
    }

    public Genre addCategory(final CategoryId aCategoryId){
        if (aCategoryId == null){
            return this;
        }
        this.categories.add(aCategoryId);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre addCategories(final List<CategoryId> categories) {
        if (categories == null || categories.isEmpty()) {
            return this;
        }
        this.categories.addAll(categories);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre removeCategory(final CategoryId aCategoryId){
        if (aCategoryId == null){
            return this;
        }
        this.categories.remove(aCategoryId);
        this.updatedAt = InstantUtils.now();
        return this;
    }
}
