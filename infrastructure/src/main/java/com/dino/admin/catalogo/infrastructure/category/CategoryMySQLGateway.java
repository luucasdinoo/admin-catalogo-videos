package com.dino.admin.catalogo.infrastructure.category;

import com.dino.admin.catalogo.domain.category.Category;
import com.dino.admin.catalogo.domain.category.CategoryGateway;
import com.dino.admin.catalogo.domain.category.CategoryId;
import com.dino.admin.catalogo.domain.category.CategorySearchQuery;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(Category aCategory) {
        return null;
    }

    @Override
    public void deleteById(CategoryId anId) {

    }

    @Override
    public Optional<Category> findById(CategoryId anId) {
        return Optional.empty();
    }

    @Override
    public Category update(Category aCategory) {
        return null;
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery aQuery) {
        return null;
    }
}
