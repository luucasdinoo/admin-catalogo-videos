package com.dino.admin.catalogo.domain.castmember;

import com.dino.admin.catalogo.domain.validation.Error;
import com.dino.admin.catalogo.domain.validation.ValidationHandler;
import com.dino.admin.catalogo.domain.validation.Validator;

public class CastMemberValidator extends Validator {

    private static final int NAME_MAX_LENGTH = 255;
    private static final int NAME_MIN_LENGTH = 3;

    private final CastMember castMember;

    public CastMemberValidator(final CastMember castMember, final ValidationHandler aHandler) {
        super(aHandler);
        this.castMember = castMember;
    }

    @Override
    public void validate() {
        checkNameConstraint();
        checkTypeConstraint();
    }

    private void checkNameConstraint() {
        final String name = this.castMember.getName();
        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }
        if (name.isBlank()){
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }
        if (name.trim().length() > NAME_MAX_LENGTH || name.trim().length() < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error ("'name' must be between 3 and 255 characters"));
        }
    }

    private void checkTypeConstraint() {
        final var type = this.castMember.getType();
        if (type == null){
            this.validationHandler().append(new Error ("'type' should not be null"));
        }
    }
}
