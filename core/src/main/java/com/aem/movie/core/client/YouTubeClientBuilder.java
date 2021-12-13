package com.aem.movie.core.client;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class YouTubeClientBuilder {


    public static YouTubeClient buildYouTubeClient(String apiUrl, String apiKey) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(new YouTubeErrorDecoder())
                .requestInterceptor(new YouTubeRequestInterceptor(apiKey))
                .target(YouTubeClient.class, apiUrl);
    }

    private YouTubeClientBuilder() {
        throw new IllegalStateException("Utility class. Cannot be instantiated.");
    }
}
