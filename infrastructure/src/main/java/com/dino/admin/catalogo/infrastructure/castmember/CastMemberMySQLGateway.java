package com.dino.admin.catalogo.infrastructure.castmember;

import com.dino.admin.catalogo.domain.castmember.CastMember;
import com.dino.admin.catalogo.domain.castmember.CastMemberGateway;
import com.dino.admin.catalogo.domain.castmember.CastMemberID;
import com.dino.admin.catalogo.domain.category.CategoryId;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.pagination.SearchQuery;
import com.dino.admin.catalogo.infrastructure.castmember.persistence.CastMemberJpaEntity;
import com.dino.admin.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import com.dino.admin.catalogo.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class CastMemberMySQLGateway implements CastMemberGateway {

    private final CastMemberRepository castMemberRepository;

    public CastMemberMySQLGateway(final CastMemberRepository castMemberRepository) {
        this.castMemberRepository = castMemberRepository;
    }

    @Override
    public CastMember create(final CastMember aCastMember) {
        return save(aCastMember);
    }

    @Override
    public void deleteById(final CastMemberID anId) {
        if (this.castMemberRepository.existsById(anId.getValue())){
            this.castMemberRepository.deleteById(anId.getValue());
        }
    }

    @Override
    public Pagination<CastMember> findAll(final SearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var where = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null );

        final var resultPage = this.castMemberRepository.findAll(where, page);

        return new Pagination<>(
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements(),
                resultPage.map(CastMemberJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public Optional<CastMember> findById(final CastMemberID anId) {
        return this.castMemberRepository.findById(anId.getValue())
                .map(CastMemberJpaEntity::toAggregate);
    }

    @Override
    public CastMember update(final CastMember aCastMember) {
        return save(aCastMember);
    }

    @Override
    public List<CastMemberID> existsByIds(Iterable<CastMemberID> castMemberIDS) {
        final var ids = StreamSupport.stream(castMemberIDS.spliterator(), false)
                .map(CastMemberID::getValue)
                .toList();

        return this.castMemberRepository.existsByIds(ids).stream()
                .map(CastMemberID::from)
                .toList();
    }

    private CastMember save(final CastMember aCastMember) {
        return this.castMemberRepository.save(CastMemberJpaEntity.from(aCastMember))
                .toAggregate();
    }

    private Specification<CastMemberJpaEntity> assembleSpecification(final String terms){
        return SpecificationUtils.like("name", terms);
    }
}
