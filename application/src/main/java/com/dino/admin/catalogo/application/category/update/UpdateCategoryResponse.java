package com.dino.admin.catalogo.application.category.update;

import com.dino.admin.catalogo.domain.category.Category;

public record UpdateCategoryResponse(
        String id
) {

    public static UpdateCategoryResponse from(final Category aCategory) {
        return new UpdateCategoryResponse(aCategory.getId().getValue());
    }

    public static UpdateCategoryResponse from(final String anId) {
        return new UpdateCategoryResponse(anId);
    }
}
