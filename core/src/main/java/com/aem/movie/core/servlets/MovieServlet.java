package com.aem.movie.core.servlets;

import com.aem.movie.core.entity.MovieEntity;
import com.aem.movie.core.services.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.List;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/movies/search",
                "sling.servlet.extensions=json",
                "sling.servlet.methods=GET"
        }
)
public class MovieServlet extends SlingSafeMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(MovieServlet.class);
    @Reference
    private MovieService movieService;

    @Override
    public void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {
        String queryString = request.getParameter("query");
        String pageResult = request.getParameter("results");
        logger.debug("Query string : {}",queryString);
        logger.debug("No of results: {}",pageResult);

        List<MovieEntity> movieEntityList = movieService.getMovieList(queryString, pageResult);

        ObjectMapper mapper = new ObjectMapper();
        String resultResponseString = mapper.writeValueAsString(movieEntityList);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "max-age=1200");
        response.getWriter().println(resultResponseString);
        response.setStatus(200);

    }
}
