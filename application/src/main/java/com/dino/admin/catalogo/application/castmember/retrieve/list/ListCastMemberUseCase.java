package com.dino.admin.catalogo.application.castmember.retrieve.list;

import com.dino.admin.catalogo.application.UseCase;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.pagination.SearchQuery;

public sealed abstract class ListCastMemberUseCase
        extends UseCase<SearchQuery, Pagination<CastMemberListOutput>>
            permits DefaultListCastMemberUseCase {
}
