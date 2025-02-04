package com.dino.admin.catalogo.application.category.create;

import com.dino.admin.catalogo.domain.category.Category;

public record CreateCategoryResponse(
        String id
) {

    public static CreateCategoryResponse from(final String anId) {
        return new CreateCategoryResponse(anId);
    }

    public static CreateCategoryResponse from(final Category aCategory) {
        return new CreateCategoryResponse(aCategory.getId().getValue());
    }
}
