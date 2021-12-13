package com.aem.movie.core.client;

import com.aem.movie.core.dto.Movie;
import com.aem.movie.core.dto.TMDBResponse;
import feign.Param;
import feign.RequestLine;

public interface TMDBClient {

    @RequestLine("GET /search/movie?query={query}")
    TMDBResponse getMovies(@Param("query") String query);

    @RequestLine("GET /movie/{id}?append_to_response=videos")
    Movie getMovie(@Param("id") int id);
}
