package com.aem.movie.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
        name = "Movie Configuration",
        description = "Configuration for integration with movie API's"
)
public @interface MovieConfiguration {
    @AttributeDefinition(name = "TMDB API Key", description = "API Key for TMDB")
    String getTMDBApiKey();

    @AttributeDefinition(name = "TMDB API Base Url", description = "API Base url for TMDB")
    String getTMDBMovieUrl();

    @AttributeDefinition(name = "Image Base Url", description = "Image Base url for TMDB")
    String getTMDBImageUrl();

    @AttributeDefinition(name = "YouTube API Key", description = "API Key for YouTube")
    String getYouTubeApiKey();

    @AttributeDefinition(name = "YouTube API Base Url", description = "API Base url for YouTube")
    String getYoutubeUrl();
}
