package com.dino.admin.catalogo.infrastructure.video.persistence;

import com.dino.admin.catalogo.domain.video.Video;
import com.dino.admin.catalogo.domain.video.VideoPreview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface VideoRepository extends JpaRepository<VideoJpaEntity, String> {

    @Query("""
            select new com.dino.admin.catalogo.domain.video.VideoPreview(
                v.id as id,
                v.title as title,
                v.description as description,
                v.createdAt as createdAt,
                v.updatedAt as updatedAt
            )
            from Video v
                join v.castMembers members
                join v.categories categories
                join v.genres genres
            where(
                :terms is null or UPPER(v.title) like :terms)
            and(
                :castMembers is null or members.id.castMemberId in :castMembers)
            and(
                :categories is null or categories.id.categoryId in :categories)
            and(
                :genres is null or genres.id.genreId in :genres)
            """)
    Page<VideoPreview> findAll(
            @Param("terms") String terms,
            @Param("castMembers") Set<String> castMembers,
            @Param("categories") Set<String> categories,
            @Param("genres") Set<String> genres,
            Pageable page
    );

}
