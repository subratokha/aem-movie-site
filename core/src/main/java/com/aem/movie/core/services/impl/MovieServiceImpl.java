package com.aem.movie.core.services.impl;

import com.aem.movie.core.client.*;
import com.aem.movie.core.dto.*;
import com.aem.movie.core.entity.MovieEntity;
import com.aem.movie.core.services.MovieConfiguration;
import com.aem.movie.core.services.MovieService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component(
        service = MovieService.class,
        immediate = true,
        configurationPolicy = ConfigurationPolicy.REQUIRE
)
@Designate(
        ocd = MovieConfiguration.class
)
public class MovieServiceImpl implements MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);
    private static final String YOU_TUBE = "YouTube";

    private String movieApiUrl;
    private String imageApiUrl;
    private String movieApiKey;
    private String youTubeApiKey;
    private String youTubeUrl;

    @Activate
    public void activate(MovieConfiguration movieConfiguration) {
        this.imageApiUrl = movieConfiguration.getTMDBImageUrl();
        this.movieApiUrl = movieConfiguration.getTMDBMovieUrl();
        this.movieApiKey = movieConfiguration.getTMDBApiKey();
        this.youTubeApiKey = movieConfiguration.getYouTubeApiKey();
        this.youTubeUrl = movieConfiguration.getYoutubeUrl();
    }

    /**
     * Get List of movies from TMDB api
     *
     * @param query query string
     * @return TMDB response
     */
    @Override
    public TMDBResponse getMovies(String query) {
        try {
            TMDBClient TMDBClient = TMDBClientBuilder.buildMovieClient(movieApiUrl, movieApiKey);
            return TMDBClient.getMovies(query);
        } catch (TMDBClientException e) {
            LOGGER.error("Error getting movie list from TMDB: {}", e.getErrorMessage());
        }
        return null;
    }

    /**
     * Get Details of movie
     *
     * @param id movie id
     * @return Movie object
     */
    @Override
    public Movie getMovieDetails(int id) {
        try {
            TMDBClient TMDBClient = TMDBClientBuilder.buildMovieClient(movieApiUrl, movieApiKey);
            return TMDBClient.getMovie(id);
        } catch (TMDBClientException e) {
            LOGGER.error("Error getting movie details from TMDB: {}", e.getErrorMessage());
        }
        return null;
    }

    /**
     * Get videos from YouTube
     *
     * @param query      query string
     * @param pageResult no of results
     * @return YouTube result
     */
    @Override
    public YouTubeResponse getYouTubeVideos(String query, String pageResult) {
        try {
            YouTubeClient youTubeClient = YouTubeClientBuilder.buildYouTubeClient(youTubeUrl, youTubeApiKey);
            return youTubeClient.getYouTubeVideos(query, pageResult);
        } catch (YouTubeClientException e) {
            LOGGER.error("Error getting movie list from YouTube : {}", e.getErrorMessage());
        }
        return null;
    }

    /**
     * Get List of combined movie list from TMDB and YouTube
     *
     * @param query      query string
     * @param pageResult results per page
     * @return List of movies
     */
    @Override
    public List<MovieEntity> getMovieList(String query, String pageResult) {
        TMDBResponse tmdbResponse = getMovies(query);
        YouTubeResponse youTubeResponse = getYouTubeVideos(query, pageResult);
        List<MovieEntity> tmdbMovieList = tmdbResponse
                .getResults()
                .stream()
                .map(this::convertTMDBResult).collect(Collectors.toList());

        List<MovieEntity> youTubeMovieList = youTubeResponse.getItems()
                .stream()
                .map(this::convertYouTubeResult).collect(Collectors.toList());

        List<MovieEntity> finalList = mergeList(tmdbMovieList, youTubeMovieList);
        return finalList.stream().limit(Integer.parseInt(pageResult)).collect(Collectors.toList());
    }

    /**
     * Convert TMDB object to MovieObject
     *
     * @param tmdbResultsItem TMDB results
     * @return MovieEntity
     */
    private MovieEntity convertTMDBResult(TMDBResultsItem tmdbResultsItem) {
        Movie movie = getMovieDetails(tmdbResultsItem.getId());
        List<ResultsItem> resultsItems = movie.getVideos().getResults().parallelStream()
                .filter(resultsItem -> resultsItem.getSite().equals(YOU_TUBE))
                .collect(Collectors.toList());
        String youTubeId = !resultsItems.isEmpty() ? resultsItems.get(0).getId() : null;

        return MovieEntity.Builder.aMovie()
                .setTitle(tmdbResultsItem.getTitle())
                .setDescription(tmdbResultsItem.getOverview())
                .setImageUrl(imageApiUrl + tmdbResultsItem.getPosterPath())
                .setYouTubeId(youTubeId).build();
    }

    /**
     * Convert YouTube response to MovieEntity object
     *
     * @param item YouTube Item
     * @return MovieEntity
     */
    private MovieEntity convertYouTubeResult(ItemsItem item) {
        return MovieEntity.Builder.aMovie()
                .setTitle(item.getSnippet().getTitle())
                .setDescription(item.getSnippet().getDescription())
                .setImageUrl(item.getSnippet().getThumbnails().getHigh().getUrl())
                .setYouTubeId(item.getId().getVideoId()).build();
    }

    /**
     * Merge the list alternately
     *
     * @param tmdb    tmdb list
     * @param youTube youTube List
     * @return finalList
     */
    private List<MovieEntity> mergeList(List<MovieEntity> tmdb, List<MovieEntity> youTube) {
        List<MovieEntity> finalList = new ArrayList<>();
        Iterator<MovieEntity> iteratorTMDB = tmdb.iterator();
        Iterator<MovieEntity> iteratorYouTube = youTube.iterator();

        while (iteratorTMDB.hasNext() || iteratorYouTube.hasNext()) {
            if (iteratorTMDB.hasNext()) {
                finalList.add(iteratorTMDB.next());
            }
            if (iteratorYouTube.hasNext()) {
                finalList.add(iteratorYouTube.next());
            }
        }
        return finalList;
    }
}
