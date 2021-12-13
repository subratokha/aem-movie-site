package com.aem.movie.core.client;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class TMDBClientBuilder {

    public static TMDBClient buildMovieClient(String apiUrl, String apiToken) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder( new TMDBErrorDecoder())
                .requestInterceptor(new MovieClientRequestInterceptor(apiToken))
                .target(TMDBClient.class, apiUrl);
    }
    private TMDBClientBuilder() {
        throw new IllegalStateException("Utility class. Cannot be instantiated.");
    }
}
