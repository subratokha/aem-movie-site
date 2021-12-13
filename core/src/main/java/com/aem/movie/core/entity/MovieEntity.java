package com.aem.movie.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieEntity {
    private String title;
    private String description;
    private String imageUrl;
    private String youTubeId;

    private MovieEntity(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
        this.youTubeId = builder.youTubeId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getYouTubeId() {
        return youTubeId;
    }

    public static final class Builder {

        private String title;
        private String description;
        private String imageUrl;
        private String youTubeId;

        public static Builder aMovie() {
            return new Builder();
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setYouTubeId(String id) {
            this.youTubeId = id;
            return this;
        }

        public MovieEntity build() {
            return new MovieEntity(this);
        }
    }

}
