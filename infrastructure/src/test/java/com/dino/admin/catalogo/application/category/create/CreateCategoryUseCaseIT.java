package com.dino.admin.catalogo.application.category.create;

import com.dino.admin.catalogo.IntegrationTest;
import com.dino.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class CreateCategoryUseCaseIT {

    @Autowired
    private CreateCategoryUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void test(){
        Assertions.assertNotNull(useCase);
        Assertions.assertNotNull(repository);
    }
}
