package com.dino.admin.catalogo.infrastructure.video.presenters;

import com.dino.admin.catalogo.application.video.retrieve.get.VideoOutput;
import com.dino.admin.catalogo.application.video.retrieve.list.VideoListOutput;
import com.dino.admin.catalogo.application.video.update.UpdateVideoOutput;
import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.domain.video.AudioVideoMedia;
import com.dino.admin.catalogo.domain.video.ImageMedia;
import com.dino.admin.catalogo.infrastructure.video.models.*;

public interface VideoApiPresenter {

    static VideoResponse present(final VideoOutput output){
        return new VideoResponse(
          output.id(),
          output.title(),
          output.description(),
          output.launchedAt(),
          output.duration(),
          output.opened(),
          output.published(),
          output.rating().getName(),
          output.createdAt(),
          output.updatedAt(),
          present(output.banner()),
          present(output.thumbnail()),
          present(output.thumbnailHalf()),
          present(output.video()),
          present(output.trailer()),
          output.categories(),
          output.genres(),
          output.castMembers()
        );
    }

    static ImageMediaResponse present(final ImageMedia image) {
        if (image == null){
            return null;
        }
        return new ImageMediaResponse(
                image.id(),
                image.checkSum(),
                image.name(),
                image.location()
        );
    }

    static AudioVideoMediaResponse present(final AudioVideoMedia media) {
        if (media == null){
            return null;
        }
        return new AudioVideoMediaResponse(
                media.id(),
                media.checksum(),
                media.name(),
                media.rawLocation(),
                media.encodedLocation(),
                media.status().name()
        );
    }

    static UpdateVideoResponse present(final UpdateVideoOutput output){
        return new UpdateVideoResponse(output.id());
    }

    static VideoListResponse present(final VideoListOutput output){
        return new VideoListResponse(
          output.id(),
          output.title(),
          output.description(),
          output.createdAt(),
          output.updatedAt()
        );
    }

    static Pagination<VideoListResponse> present(final Pagination<VideoListOutput> page){
        return page.map(VideoApiPresenter::present);
    }
}
