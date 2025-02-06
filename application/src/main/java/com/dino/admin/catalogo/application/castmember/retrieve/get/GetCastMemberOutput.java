package com.dino.admin.catalogo.application.castmember.retrieve.get;

import com.dino.admin.catalogo.domain.castmember.CastMember;
import com.dino.admin.catalogo.domain.castmember.CastMemberType;

import java.time.Instant;

public record GetCastMemberOutput(
        String id,
        String name,
        CastMemberType type,
        Instant createdAt,
        Instant updatedAt
) {

    public static GetCastMemberOutput from(final CastMember aMember){
        return new GetCastMemberOutput(
                aMember.getId().getValue(),
                aMember.getName(),
                aMember.getType(),
                aMember.getCreatedAt(),
                aMember.getUpdatedAt()
        );
    }
}
