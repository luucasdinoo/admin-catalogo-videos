package com.dino.admin.catalogo.application.castmember.update;

import com.dino.admin.catalogo.application.UseCase;

public sealed abstract class UpdateCastMemberUseCase
        extends UseCase<UpdateCastMemberCommand, UpdateCastMemberOutput>
            permits DefaultUpdateCastMemberUseCase {
}
