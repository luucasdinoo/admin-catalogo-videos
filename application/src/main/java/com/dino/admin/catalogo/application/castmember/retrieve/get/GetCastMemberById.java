package com.dino.admin.catalogo.application.castmember.retrieve.get;

import com.dino.admin.catalogo.application.UseCase;

public sealed abstract class GetCastMemberById
        extends UseCase<String, GetCastMemberOutput>
            permits DefaultGetCastMemberByIdUseCase {
}
