package com.dino.admin.catalogo.domain.video;

import com.dino.admin.catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface VideoGateway {

    Video create(Video video);

    void deleteById(VideoID anId);

    Video update(Video video);

    Optional<Video> findById(VideoID anId);

    Pagination<VideoPreview>findAll(VideoSearchQuery aQuery);

}
