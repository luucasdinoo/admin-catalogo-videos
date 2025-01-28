package com.dino.admin.catalogo.application.category.create;

public record CreateCategoryRequest(
        String name,
        String description,
        boolean isActive
) {

    public static CreateCategoryRequest with(
            final String aName,
            final String aDescription,
            final boolean isActive
    ){
        return new CreateCategoryRequest(aName, aDescription, isActive);
    }
}
