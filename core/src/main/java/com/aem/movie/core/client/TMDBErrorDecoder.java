package com.aem.movie.core.client;

import com.aem.movie.core.dto.TMDBErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.http.HttpStatus;

import java.io.IOException;

public class TMDBErrorDecoder implements ErrorDecoder {
    @Override

    public Exception decode(String methodKey, Response response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TMDBErrorResponse errorResponse = mapper.readValue(response.body().asInputStream(), TMDBErrorResponse.class);
            return new TMDBClientException(response.request().url(), response.status(), errorResponse.getStatusCode(), errorResponse.getStatusMessage());
        } catch (IOException e) {
            return new TMDBClientException(response.request().url(), response.status(), HttpStatus.SC_BAD_REQUEST, "Unknown error message");
        }
    }

}
