package com.dino.admin.catalogo.infrastructure.castmember.presenters;

import com.dino.admin.catalogo.application.castmember.retrieve.get.CastMemberOutput;
import com.dino.admin.catalogo.application.castmember.retrieve.list.CastMemberListOutput;
import com.dino.admin.catalogo.infrastructure.castmember.models.CastMemberListResponse;
import com.dino.admin.catalogo.infrastructure.castmember.models.CastMemberResponse;

public interface CastMemberPresenter {

    static CastMemberResponse present(final CastMemberOutput output){
        return new CastMemberResponse(
                output.id(),
                output.name(),
                output.type().name(),
                output.createdAt().toString(),
                output.createdAt().toString()
        );
    }

    static CastMemberListResponse present(final CastMemberListOutput output){
        return new CastMemberListResponse(
                output.id(),
                output.name(),
                output.type().name(),
                output.createdAt().toString()
        );
    }
}
