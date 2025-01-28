package com.dino.admin.catalogo.domain.category;

import com.dino.admin.catalogo.domain.validation.Error;
import com.dino.admin.catalogo.domain.validation.ValidationHandler;
import com.dino.admin.catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 3;
    private final Category category;

    public CategoryValidator(final Category aCategory, final ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        checkNameConstraint();
    }

    private void checkNameConstraint() {
        final String name = this.category.getName();
        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }
        if (name.isBlank()){
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }
        if (name.trim().length() > NAME_MAX_LENGTH || name.trim().length() < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}
