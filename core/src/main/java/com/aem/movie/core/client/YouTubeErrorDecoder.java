package com.aem.movie.core.client;

import com.aem.movie.core.dto.Error;
import com.aem.movie.core.dto.YouTubeErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.http.HttpStatus;

import java.io.IOException;

public class YouTubeErrorDecoder implements ErrorDecoder {
    @Override

    public Exception decode(String methodKey, Response response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            YouTubeErrorResponse errorResponse = mapper.readValue(response.body().asInputStream(), YouTubeErrorResponse.class);
            Error error = errorResponse.getError();
            return new YouTubeClientException(response.request().url(), response.status(), error.getCode(), error.getMessage());
        } catch (IOException e) {
            return new YouTubeClientException(response.request().url(), response.status(), HttpStatus.SC_BAD_REQUEST, "Unknown error message");
        }
    }

}
