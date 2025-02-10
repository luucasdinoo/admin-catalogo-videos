package com.dino.admin.catalogo.domain.castmember;

import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface CastMemberGateway {

    CastMember create(CastMember aCastMember);

    void deleteById(CastMemberID anId);

    Pagination<CastMember> findAll(SearchQuery aQuery);

    Optional<CastMember> findById(CastMemberID anId);

    CastMember update(CastMember aCastMember);

    List<CastMemberID> existsByIds(Iterable<CastMemberID> ids);
}
