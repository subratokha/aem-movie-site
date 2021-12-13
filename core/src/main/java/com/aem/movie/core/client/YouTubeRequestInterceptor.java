package com.aem.movie.core.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class YouTubeRequestInterceptor implements RequestInterceptor {

    private final String apiKey;
    public YouTubeRequestInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.query("key",apiKey);
    }
}
