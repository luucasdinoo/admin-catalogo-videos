package com.dino.admin.catalogo.infrastructure.castmember.models;

import com.dino.admin.catalogo.domain.castmember.CastMemberType;

public record UpdateCastMemberRequest(String name, CastMemberType type) {
}
