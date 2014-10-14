package com.xebiatest.boxotop.app.Job;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.xebiatest.boxotop.app.BoxotopApp;
import com.xebiatest.boxotop.app.events.FetchMovieEvent;
import com.xebiatest.boxotop.app.events.NetworkErrorEvent;
import com.xebiatest.boxotop.app.model.Movie;
import com.xebiatest.boxotop.app.model.MoviesWrapper;
import com.xebiatest.boxotop.app.service.BoxotopApiService;
import com.path.android.jobqueue.network.NetworkUtil;
import com.path.android.jobqueue.network.NetworkUtilImpl;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by pierrecastex on 27/09/2014.
 */
public class FetchMovieJob extends BaseNetworkJob {
    public static final int PRIORITY = 1;

    @Inject
    BoxotopApiService boxotopApiClient;

    private long movieId;

    public FetchMovieJob(long movieId) {
        super();
        BoxotopApp.injectMembers(this);
        this.movieId = movieId;
    }

    @Override
    public void onAdded() {
 	
    }

    @Override
    public void onRun() throws Throwable {
        Movie movie = boxotopApiClient.getMovie(movieId);

        MoviesWrapper similarMovies = boxotopApiClient.getSimilarMovies(movieId);
        movie.setSimilarMovies(similarMovies.getMovies());

        FetchMovieEvent event = new FetchMovieEvent(movie);
        eventBus.post(event);


    }

    @Override
    protected void onCancel() {

    }

}
