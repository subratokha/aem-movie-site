package com.aem.movie.core.services;

import com.aem.movie.core.dto.Movie;
import com.aem.movie.core.dto.TMDBResponse;
import com.aem.movie.core.dto.YouTubeResponse;
import com.aem.movie.core.entity.MovieEntity;

import java.util.List;

public interface MovieService {

    TMDBResponse getMovies(String query);

    Movie getMovieDetails(int id);
    YouTubeResponse getYouTubeVideos(String query,String pageResult);
    List<MovieEntity> getMovieList(String query,String pageResult);
}
