package com.dino.admin.catalogo.application.castmember.delete;

import com.dino.admin.catalogo.domain.castmember.CastMemberGateway;
import com.dino.admin.catalogo.domain.castmember.CastMemberID;

import java.util.Objects;

public final class DefaultDeleteCastMemberUseCase extends DeleteCastMemberUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultDeleteCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public void execute(String anIn) {
        this.castMemberGateway.deleteById(CastMemberID.from(anIn));
    }
}
