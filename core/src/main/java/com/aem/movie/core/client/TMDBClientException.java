package com.aem.movie.core.client;

public class TMDBClientException extends RuntimeException{

    private String errorMessage;
    private static final String DEFAULT_MESSAGE = "Error while sending request to TMDB.\nRequest URL: %s\nResponse status: %d\nError code: %d\nMessage: %s";
    public TMDBClientException(String url, int status, int errorCode, String message ) {
        super(createMessage(url, status, errorCode, message));
        this.errorMessage = message;
    }

    private static String createMessage(final String url, final int responseStatus, final int errorCode, final String message) {
        return String.format(
                DEFAULT_MESSAGE,
                url,
                responseStatus,
                errorCode,
                message);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
