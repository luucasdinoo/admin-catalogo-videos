package com.dino.admin.catalogo.infrastructure.genre.persistence;

import com.dino.admin.catalogo.domain.category.CategoryId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "genres_categories")
public class GenreCategoryJpaEntity {

    @EmbeddedId
    private GenreCategoryId id;

    @ManyToOne
    @MapsId("genreId")
    private GenreJpaEntity genre;

    public GenreCategoryJpaEntity(){}

    private GenreCategoryJpaEntity(final GenreJpaEntity aGenre, final CategoryId aCategoryId){
        this.id = GenreCategoryId.from(aGenre.getId(), aCategoryId.getValue());
        this.genre = aGenre;
    }

    public static GenreCategoryJpaEntity from(final GenreJpaEntity aGenre, final CategoryId aCategoryId){
        return new GenreCategoryJpaEntity(aGenre, aCategoryId);
    }

    public GenreCategoryId getId() {
        return id;
    }

    public void setId(GenreCategoryId id) {
        this.id = id;
    }

    public GenreJpaEntity getGenre() {
        return genre;
    }

    public void setGenre(GenreJpaEntity genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GenreCategoryJpaEntity that = (GenreCategoryJpaEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
