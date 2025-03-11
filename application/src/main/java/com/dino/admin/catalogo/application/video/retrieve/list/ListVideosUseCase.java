package com.dino.admin.catalogo.application.video.retrieve.list;

import com.dino.admin.catalogo.application.UseCase;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.video.VideoSearchQuery;

public abstract class ListVideosUseCase extends UseCase<VideoSearchQuery, Pagination<VideoListOutput>> {
}
