package com.dino.admin.catalogo.application.castmember.delete;

import com.dino.admin.catalogo.application.UnitUseCase;

public sealed abstract class DeleteCastMemberUseCase
        extends UnitUseCase<String>
            permits DefaultDeleteCastMemberUseCase {
}
