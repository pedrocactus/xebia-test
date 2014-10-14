package com.xebiatest.boxotop.app.events;

import com.xebiatest.boxotop.app.model.Movie;

import java.util.List;

/**
 * Created by pierrecastex on 20/09/2014.
 */
public class FetchMovieListEvent {
    List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public FetchMovieListEvent(List<Movie> movies) {

        this.movies = movies;
    }
}
