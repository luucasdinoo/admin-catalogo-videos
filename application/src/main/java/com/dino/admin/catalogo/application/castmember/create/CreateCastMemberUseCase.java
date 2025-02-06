package com.dino.admin.catalogo.application.castmember.create;

import com.dino.admin.catalogo.application.UseCase;

public sealed abstract class CreateCastMemberUseCase
    extends UseCase<CreateCastMemberCommand, CreateCastMemberOutput>
        permits DefaultCreateCastMemberUseCase {
}
