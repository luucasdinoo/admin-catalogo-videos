package com.dino.admin.catalogo.application.video.update;

import com.dino.admin.catalogo.domain.resource.Resource;

import java.util.Optional;
import java.util.Set;

public record UpdateVideoCommand(
        String id,
        String title,
        String description,
        int launchedAt,
        double duration,
        boolean opened,
        boolean published,
        String rating,
        Set<String> categories,
        Set<String> genres,
        Set<String> members,
        Resource video,
        Resource trailer,
        Resource banner,
        Resource thumbnail,
        Resource thumbnailHalf
) {
    public static UpdateVideoCommand with(
            final String id,
            final String title,
            final String description,
            final int launchedAt,
            final double duration,
            final boolean opened,
            final boolean published,
            final String rating,
            final Set<String> categories,
            final Set<String> genres,
            final Set<String> members,
            final Resource video,
            final Resource trailer,
            final Resource banner,
            final Resource thumbnail,
            final Resource thumbnailHalf
    ){
        return new UpdateVideoCommand(
                id,
                title,
                description,
                launchedAt,
                duration,
                opened,
                published,
                rating,
                categories,
                genres,
                members,
                video,
                trailer,
                banner,
                thumbnail,
                thumbnailHalf
        );
    }
    public static UpdateVideoCommand with(
            final String id,
            final String title,
            final String description,
            final int launchedAt,
            final double duration,
            final boolean opened,
            final boolean published,
            final String rating,
            final Set<String> categories,
            final Set<String> genres,
            final Set<String> members
    ){
        return  with(
                id,
                title,
                description,
                launchedAt,
                duration,
                opened,
                published,
                rating,
                categories,
                genres,
                members,
                null,
                null,
                null,
                null,
                null
        );
    }


    public Optional<Resource> getVideo(){
        return Optional.ofNullable(video);
    }

    public Optional<Resource> getTrailer(){
        return Optional.ofNullable(trailer);
    }

    public Optional<Resource> getBanner(){
        return Optional.ofNullable(banner);
    }

    public Optional<Resource> getThumbnail(){
        return Optional.ofNullable(thumbnail);
    }

    public Optional<Resource> getThumbnailHalf(){
        return Optional.ofNullable(thumbnailHalf);
    }
}
