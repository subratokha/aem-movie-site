package com.aem.movie.core.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class MovieClientRequestInterceptor implements RequestInterceptor {

    private final String apiToken;

    public MovieClientRequestInterceptor(String apiToken) {
        this.apiToken = apiToken;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", "Bearer " + apiToken);
    }
}
