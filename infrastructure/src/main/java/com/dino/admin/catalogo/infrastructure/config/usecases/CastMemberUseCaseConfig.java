package com.dino.admin.catalogo.infrastructure.config.usecases;

import com.dino.admin.catalogo.application.castmember.create.CreateCastMemberUseCase;
import com.dino.admin.catalogo.application.castmember.create.DefaultCreateCastMemberUseCase;
import com.dino.admin.catalogo.application.castmember.delete.DefaultDeleteCastMemberUseCase;
import com.dino.admin.catalogo.application.castmember.delete.DeleteCastMemberUseCase;
import com.dino.admin.catalogo.application.castmember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import com.dino.admin.catalogo.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import com.dino.admin.catalogo.application.castmember.retrieve.list.DefaultListCastMemberUseCase;
import com.dino.admin.catalogo.application.castmember.retrieve.list.ListCastMemberUseCase;
import com.dino.admin.catalogo.application.castmember.update.DefaultUpdateCastMemberUseCase;
import com.dino.admin.catalogo.application.castmember.update.UpdateCastMemberUseCase;
import com.dino.admin.catalogo.domain.castmember.CastMemberGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CastMemberUseCaseConfig {

    private final CastMemberGateway castMemberGateway;

    public CastMemberUseCaseConfig(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Bean
    public CreateCastMemberUseCase createCastMemberUseCase() {
        return new DefaultCreateCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public DeleteCastMemberUseCase deleteCastMemberUseCase() {
        return new DefaultDeleteCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public GetCastMemberByIdUseCase getCastMemberByIdUseCase() {
        return new DefaultGetCastMemberByIdUseCase(castMemberGateway);
    }

    @Bean
    public ListCastMemberUseCase listCastMembersUseCase() {
        return new DefaultListCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public UpdateCastMemberUseCase updateCastMemberUseCase() {
        return new DefaultUpdateCastMemberUseCase(castMemberGateway);
    }

}
