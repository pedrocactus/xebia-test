package com.xebiatest.boxotop.app.Job;

import com.xebiatest.boxotop.app.BoxotopApp;
import com.xebiatest.boxotop.app.events.FetchMovieListEvent;
import com.xebiatest.boxotop.app.events.NetworkErrorEvent;
import com.xebiatest.boxotop.app.model.MoviesWrapper;
import com.xebiatest.boxotop.app.model.Movie;
import com.xebiatest.boxotop.app.service.BoxotopApiService;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by pierrecastex on 20/09/2014.
 */
public class FetchMoviesJob extends BaseNetworkJob {

    @Inject
    BoxotopApiService boxotopApiClient;

    // This job requires network connectivity,
    // and should be persisted in case the application exits before job is completed.
    public FetchMoviesJob() {
        super();
        BoxotopApp.injectMembers(this);
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        MoviesWrapper movieWrapper = null;
        movieWrapper = boxotopApiClient.getMovieList();

        List<Movie> movies = movieWrapper.getMovies();
        for (Movie movie : movies ) {
            System.out.println(movie.getTitle() + " (" + movie.getYear() + ")");
        }
        FetchMovieListEvent event = new FetchMovieListEvent(movies);
        eventBus.post(event);
    }

    @Override
    protected void onCancel() {

    }

}
