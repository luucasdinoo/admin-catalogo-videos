package com.dino.admin.catalogo.infrastructure.video.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<VideoJpaEntity, String> {
}
