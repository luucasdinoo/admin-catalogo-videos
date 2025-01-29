package com.dino.admin.catalogo.application.category.update;

import com.dino.admin.catalogo.domain.category.Category;
import com.dino.admin.catalogo.domain.category.CategoryId;

public record UpdateCategoryResponse(
        CategoryId id
) {

    public static UpdateCategoryResponse from(final Category aCategory) {
        return new UpdateCategoryResponse(aCategory.getId());
    }
}
