package com.dino.admin.catalogo.infrastructure.config.usecases;

import com.dino.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.dino.admin.catalogo.application.category.create.DefaultCreateCategoryUseCase;
import com.dino.admin.catalogo.application.category.delete.DefaultDeleteCategoryUseCase;
import com.dino.admin.catalogo.application.category.delete.DeleteCategoryUseCase;
import com.dino.admin.catalogo.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.dino.admin.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.dino.admin.catalogo.application.category.retrieve.list.DefaultListCategoryUseCase;
import com.dino.admin.catalogo.application.category.retrieve.list.ListCategoryUseCase;
import com.dino.admin.catalogo.application.category.update.DefaultUpdateCategoryUseCase;
import com.dino.admin.catalogo.application.category.update.UpdateCategoryUseCase;
import com.dino.admin.catalogo.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public ListCategoryUseCase listCategoriesUseCase() {
        return new DefaultListCategoryUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }
}
