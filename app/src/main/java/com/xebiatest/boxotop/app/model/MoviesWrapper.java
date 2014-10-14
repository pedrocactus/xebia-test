package com.xebiatest.boxotop.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by pierrecastex on 21/09/2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoviesWrapper {

    List<Movie> movies;

    public MoviesWrapper() {
    }

    public MoviesWrapper(List<Movie> movies) {

        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
