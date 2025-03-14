package com.dino.admin.catalogo.infrastructure.video;

import com.dino.admin.catalogo.domain.Identifier;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.video.*;
import com.dino.admin.catalogo.infrastructure.config.annotations.VideoCreatedQueue;
import com.dino.admin.catalogo.infrastructure.services.EventService;
import com.dino.admin.catalogo.infrastructure.video.persistence.VideoJpaEntity;
import com.dino.admin.catalogo.infrastructure.video.persistence.VideoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.dino.admin.catalogo.domain.utils.CollectionUtils.*;
import static com.dino.admin.catalogo.infrastructure.utils.SqlUtils.*;


@Component
public class DefaultVideoGateway implements VideoGateway {

    private final VideoRepository videoRepository;
    private final EventService eventService;

    public DefaultVideoGateway(
            final VideoRepository videoRepository,
            @VideoCreatedQueue final EventService eventService) {
        this.videoRepository = Objects.requireNonNull(videoRepository);
        this.eventService = Objects.requireNonNull(eventService);
    }

    @Override
    @Transactional
    public Video create(final Video aVideo) {
        return save(aVideo);
    }

    @Override
    public void deleteById(final VideoID anId) {
        if (videoRepository.existsById(anId.getValue())){
            videoRepository.deleteById(anId.getValue());
        }
    }

    @Override
    @Transactional
    public Video update(final Video aVideo) {
        return save(aVideo);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Video> findById(final VideoID anId) {
        return this.videoRepository.findById(anId.getValue())
                .map(VideoJpaEntity::toAggregate);
    }

    @Override
    public Pagination<VideoPreview> findAll(final VideoSearchQuery aQuery) {
        PageRequest page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        Page<VideoPreview> actualPage = this.videoRepository.findAll(
                like(aQuery.terms()),
                nullIfEmpty(mapTo(aQuery.castMembers(), Identifier::getValue)),
                nullIfEmpty(mapTo(aQuery.categories(), Identifier::getValue)),
                nullIfEmpty(mapTo(aQuery.genres(), Identifier::getValue)),
                page
        );

        return new Pagination<>(
                actualPage.getNumber(),
                actualPage.getSize(),
                actualPage.getTotalElements(),
                actualPage.toList()
        );
    }


    private Video save(Video aVideo) {
        final var result = this.videoRepository.save(VideoJpaEntity.from(aVideo))
                .toAggregate();

        aVideo.publishDomainEvent(this.eventService::send);
        return result;
    }
}
