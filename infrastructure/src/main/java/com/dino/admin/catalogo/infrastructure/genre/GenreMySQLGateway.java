package com.dino.admin.catalogo.infrastructure.genre;

import com.dino.admin.catalogo.domain.genre.Genre;
import com.dino.admin.catalogo.domain.genre.GenreGateway;
import com.dino.admin.catalogo.domain.genre.GenreID;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.pagination.SearchQuery;
import com.dino.admin.catalogo.infrastructure.genre.persistence.GenreJpaEntity;
import com.dino.admin.catalogo.infrastructure.genre.persistence.GenreRepository;
import com.dino.admin.catalogo.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class GenreMySQLGateway implements GenreGateway {

    private final GenreRepository genreRepository;

    public GenreMySQLGateway(GenreRepository genreRepository) {
        this.genreRepository = Objects.requireNonNull(genreRepository);
    }

    @Override
    public Genre create(Genre aGenre) {
        return save(aGenre);
    }

    @Override
    public void deleteById(final GenreID anId) {
        if (this.genreRepository.existsById(anId.getValue())){
            this.genreRepository.deleteById(anId.getValue());
        }
    }

    @Override
    public Pagination<Genre> findAll(final SearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var where = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult =
                this.genreRepository.findAll(Specification.where(where), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(GenreJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public Optional<Genre> findById(final GenreID anId) {
        return this.genreRepository.findById(anId.getValue())
                .map(GenreJpaEntity::toAggregate);
    }

    @Override
    public Genre update(final Genre aGenre) {
        return save(aGenre);
    }

    @Override
    public List<GenreID> existsByIds(Iterable<GenreID> genreIds) {
        final var ids = StreamSupport.stream(genreIds.spliterator(), false)
                .map(GenreID::getValue)
                .toList();

        return this.genreRepository.existsByIds(ids).stream()
                .map(GenreID::from)
                .toList();
    }

    private Genre save(final Genre aGenre) {
        return this.genreRepository.save(GenreJpaEntity.from(aGenre))
                .toAggregate();
    }

    private Specification<GenreJpaEntity> assembleSpecification(final String terms){
        return SpecificationUtils.like("name", terms);
    }
}
