package com.dino.admin.catalogo.application.video.create;

import com.dino.admin.catalogo.application.UseCase;

public abstract sealed class CreateVideoUseCase
        extends UseCase<CreateVideoCommand, CreateVideoOutput>
            permits DefaultCreateVideoUseCase {
}
