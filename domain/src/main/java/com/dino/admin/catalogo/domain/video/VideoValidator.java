package com.dino.admin.catalogo.domain.video;

import com.dino.admin.catalogo.domain.validation.Error;
import com.dino.admin.catalogo.domain.validation.ValidationHandler;
import com.dino.admin.catalogo.domain.validation.Validator;

public class VideoValidator extends Validator {

    private static final int TITLE_MAX_LENGTH = 255;
    private static final int DESCRIPTION_MAX_LENGTH = 4_000;

    private final Video video;

    protected VideoValidator(final Video aVideo, final ValidationHandler aHandler) {
        super(aHandler);
        this.video = aVideo;
    }

    @Override
    public void validate() {
        checkTitleConstraint();
        checkDescriptionConstraint();
        checkLaunchedAtConstraint();
        checkRatingConstraint();
    }

    private void checkTitleConstraint() {
        final String title = this.video.getTitle();
        if (title == null) {
            this.validationHandler().append(new Error("'title' should not be null"));
            return;
        }
        if (title.isBlank()){
            this.validationHandler().append(new Error("'title' should not be empty"));
            return;
        }
        if (title.trim().length() > TITLE_MAX_LENGTH) {
            this.validationHandler().append(new Error("'title' must be between 1 and 255 characters"));
        }
    }

    private void checkDescriptionConstraint() {
        final String description = this.video.getDescription();
        if (description == null) {
            this.validationHandler().append(new Error("'description' should not be null"));
            return;
        }
        if (description.isBlank()){
            this.validationHandler().append(new Error("'description' should not be empty"));
            return;
        }
        if (description.trim().length() > DESCRIPTION_MAX_LENGTH) {
            this.validationHandler().append(new Error("'description' must be between 1 and 4000 characters"));
        }
    }

    private void checkLaunchedAtConstraint() {
        final var launchedAt = this.video.getLaunchedAt();
        if (launchedAt == null) {
            this.validationHandler().append(new Error("'launchedAt' should not be null"));
        }
    }

    private void checkRatingConstraint() {
        final var rating = this.video.getRating();
        if (rating == null) {
            this.validationHandler().append(new Error("'rating' should not be null"));
        }
    }
}
