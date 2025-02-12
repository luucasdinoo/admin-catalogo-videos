package com.dino.admin.catalogo;

import com.dino.admin.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import com.dino.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import com.dino.admin.catalogo.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;

public class MySQLCleanUpExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        final var appContext = SpringExtension.getApplicationContext(extensionContext);

        cleanUp(List.of(
                appContext.getBean(CastMemberRepository.class),
                appContext.getBean(GenreRepository.class),
                appContext.getBean(CategoryRepository.class)
        ));

        final var em = appContext.getBean(TestEntityManager.class);
        em.flush();
        em.clear();
    }

    private void cleanUp(final Collection<CrudRepository> repositories){
        repositories.forEach(CrudRepository::deleteAll);
    }
}
