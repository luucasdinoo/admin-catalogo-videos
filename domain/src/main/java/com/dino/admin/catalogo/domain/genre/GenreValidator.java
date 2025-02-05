package com.dino.admin.catalogo.domain.genre;

import com.dino.admin.catalogo.domain.validation.Error;
import com.dino.admin.catalogo.domain.validation.ValidationHandler;
import com.dino.admin.catalogo.domain.validation.Validator;

public class GenreValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 2;
    private final Genre genre;

    protected GenreValidator(final Genre aGenre, final ValidationHandler aHandler) {
        super(aHandler);
        this.genre = aGenre;
    }

    @Override
    public void validate() {
        checkNameConstraint();
    }

    private void checkNameConstraint() {
        final String name = this.genre.getName();
        if (name == null) {
            this.validationHandler().append(new com.dino.admin.catalogo.domain.validation.Error("'name' should not be null"));
            return;
        }
        if (name.isBlank()){
            this.validationHandler().append(new com.dino.admin.catalogo.domain.validation.Error("'name' should not be empty"));
            return;
        }
        if (name.trim().length() > NAME_MAX_LENGTH || name.trim().length() < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 2 and 255 characters"));
        }
    }
}
