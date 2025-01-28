package com.dino.admin.catalogo.application.category.create;

import com.dino.admin.catalogo.domain.category.Category;
import com.dino.admin.catalogo.domain.category.CategoryId;

public record CreateCategoryResponse(
        CategoryId id
) {

    public static CreateCategoryResponse from(final Category aCategory) {
        return new CreateCategoryResponse(aCategory.getId());
    }
}
