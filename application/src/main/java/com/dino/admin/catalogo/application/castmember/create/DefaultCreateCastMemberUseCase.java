package com.dino.admin.catalogo.application.castmember.create;

import com.dino.admin.catalogo.domain.castmember.CastMember;
import com.dino.admin.catalogo.domain.castmember.CastMemberGateway;
import com.dino.admin.catalogo.domain.exceptions.NotificationException;
import com.dino.admin.catalogo.domain.validation.handler.Notification;

import java.util.Objects;

public final class DefaultCreateCastMemberUseCase extends CreateCastMemberUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultCreateCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public CreateCastMemberOutput execute(CreateCastMemberCommand aCommand) {
        final var notification = Notification.create();

        CastMember aMember = notification.validate(() -> CastMember.newMember(aCommand.name(), aCommand.type()));

        if (notification.hasError()){
            throw new NotificationException("Could not create Aggregate CastMember", notification);
        }
        return CreateCastMemberOutput.from(this.castMemberGateway.create(aMember));
    }
}
