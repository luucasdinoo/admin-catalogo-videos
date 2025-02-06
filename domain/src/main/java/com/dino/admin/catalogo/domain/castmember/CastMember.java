package com.dino.admin.catalogo.domain.castmember;

import com.dino.admin.catalogo.domain.AggregateRoot;
import com.dino.admin.catalogo.domain.exceptions.NotificationException;
import com.dino.admin.catalogo.domain.utils.InstantUtils;
import com.dino.admin.catalogo.domain.validation.ValidationHandler;
import com.dino.admin.catalogo.domain.validation.handler.Notification;

import java.time.Instant;

public class CastMember extends AggregateRoot<CastMemberID> {

    private String name;
    private CastMemberType type;
    private Instant createdAt;
    private Instant updatedAt;


    protected CastMember(
            final CastMemberID anId,
            final String aName,
            final CastMemberType aType,
            final Instant aCreatedAt,
            final Instant anUpdatedAt
    ) {
        super(anId);
        this.name = aName;
        this.type = aType;
        this.createdAt = aCreatedAt;
        this.updatedAt = anUpdatedAt;

        selfValidate();
    }

    public static CastMember newMember(final String aName, final CastMemberType aType){
        final var anId = CastMemberID.unique();
        final var now = InstantUtils.now();
        return new CastMember(anId, aName, aType, now, now);
    }

    public static CastMember with(
            final CastMemberID anId,
            final String aName,
            final CastMemberType aType,
            final Instant aCreatedAt,
            final Instant anUpdatedAt
    ){
        return new CastMember(anId, aName, aType, aCreatedAt, anUpdatedAt);
    }

    public static CastMember with(final CastMember aMember){
        return new CastMember(
                aMember.getId(),
                aMember.getName(),
                aMember.getType(),
                aMember.getCreatedAt(),
                aMember.getUpdatedAt()
        );
    }

    public CastMember update(final String aName, final CastMemberType aType){
        this.name = aName;
        this.type = aType;
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    public String getName() {
        return name;
    }

    public CastMemberType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new CastMemberValidator(this, handler).validate();
    }

    private void selfValidate(){
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()){
            throw new NotificationException("Failed to create a Aggregate CastMember", notification);
        }
    }
}
