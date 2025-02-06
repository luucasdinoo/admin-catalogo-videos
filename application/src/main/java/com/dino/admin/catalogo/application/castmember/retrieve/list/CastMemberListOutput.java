package com.dino.admin.catalogo.application.castmember.retrieve.list;

import com.dino.admin.catalogo.domain.castmember.CastMember;
import com.dino.admin.catalogo.domain.castmember.CastMemberType;

import java.time.Instant;

public record CastMemberListOutput(
        String id,
        String name,
        CastMemberType type,
        Instant createdAt
) {

    public static CastMemberListOutput from (final CastMember aMember){
        return new CastMemberListOutput(
                aMember.getId().getValue(),
                aMember.getName(),
                aMember.getType(),
                aMember.getCreatedAt()
        );
    }
}
