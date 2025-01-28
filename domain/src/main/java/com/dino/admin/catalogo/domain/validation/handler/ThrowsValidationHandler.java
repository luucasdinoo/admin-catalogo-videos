package com.dino.admin.catalogo.domain.validation.handler;

import com.dino.admin.catalogo.domain.exceptions.DomainException;
import com.dino.admin.catalogo.domain.validation.Error;
import com.dino.admin.catalogo.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {

    @Override
    public ValidationHandler append(final Error anError) {
        throw DomainException.with(anError);
    }

    @Override
    public ValidationHandler append(ValidationHandler anHandler) {
        throw DomainException.with(anHandler.getErrors());
    }

    @Override
    public ValidationHandler validate(Validation aValidation) {
        try {
            aValidation.validate();

        }catch (final Exception e){
            throw DomainException.with(List.of(new Error(e.getMessage())));

        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
