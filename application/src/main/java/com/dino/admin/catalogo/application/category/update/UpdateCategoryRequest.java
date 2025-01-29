package com.dino.admin.catalogo.application.category.update;

public record UpdateCategoryRequest(
        String id,
        String name,
        String description,
        boolean isActive
) {

    public static UpdateCategoryRequest with(
            String anId,
            String aName,
            String aDescription,
            boolean isActive) {
        return new UpdateCategoryRequest(anId, aName, aDescription, isActive);
    }
}
