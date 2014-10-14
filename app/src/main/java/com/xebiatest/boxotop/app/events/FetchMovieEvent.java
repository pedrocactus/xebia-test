package com.xebiatest.boxotop.app.events;

import com.xebiatest.boxotop.app.model.Movie;

import java.util.List;

/**
 * Created by pierrecastex on 20/09/2014.
 */
public class FetchMovieEvent {
    Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public FetchMovieEvent(Movie movie) {

        this.movie = movie;
    }
}
