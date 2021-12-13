package com.aem.movie.core.client;

import com.aem.movie.core.dto.YouTubeResponse;
import feign.Param;
import feign.RequestLine;

public interface YouTubeClient {
    @RequestLine("GET /search?q={query}&type=video&part=snippet&videoType=movie&videoEmbeddable=true&maxResults={pageResult}")
    YouTubeResponse getYouTubeVideos(@Param("query") String query, @Param("pageResult") String pageResult);
}
