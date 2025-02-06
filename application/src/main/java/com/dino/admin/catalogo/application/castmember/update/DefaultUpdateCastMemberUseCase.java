package com.dino.admin.catalogo.application.castmember.update;

import com.dino.admin.catalogo.domain.castmember.CastMember;
import com.dino.admin.catalogo.domain.castmember.CastMemberGateway;
import com.dino.admin.catalogo.domain.castmember.CastMemberID;
import com.dino.admin.catalogo.domain.exceptions.NotFoundException;
import com.dino.admin.catalogo.domain.exceptions.NotificationException;
import com.dino.admin.catalogo.domain.validation.handler.Notification;

import java.util.Objects;

public final class DefaultUpdateCastMemberUseCase extends UpdateCastMemberUseCase{

    private final CastMemberGateway castMemberGateway;

    public DefaultUpdateCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public UpdateCastMemberOutput execute(final UpdateCastMemberCommand aCommand) {
        final var anId = CastMemberID.from(aCommand.id());

        final var aMember = this.castMemberGateway.findById(anId)
                .orElseThrow(()-> NotFoundException.with(CastMember.class, anId));

        final var notification = Notification.create();
        notification.validate(()-> aMember.update(aCommand.name(), aCommand.type()));

        if (notification.hasError()) {
            throw new NotificationException("Could not update Aggregate CastMember %s".formatted(anId), notification);
        }

        return UpdateCastMemberOutput.from(this.castMemberGateway.update(aMember));
    }
}
