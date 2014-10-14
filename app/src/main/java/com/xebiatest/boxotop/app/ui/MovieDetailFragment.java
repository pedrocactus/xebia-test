package com.xebiatest.boxotop.app.ui;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.path.android.jobqueue.JobManager;
import com.xebiatest.boxotop.app.BoxotopApp;
import com.xebiatest.boxotop.app.Job.FetchMovieJob;
import com.xebiatest.boxotop.app.R;
import com.xebiatest.boxotop.app.events.AddJobEvent;
import com.xebiatest.boxotop.app.events.FetchMovieEvent;
import com.xebiatest.boxotop.app.events.NetworkErrorEvent;
import com.xebiatest.boxotop.app.events.SaveCommentEvent;
import com.xebiatest.boxotop.app.model.Movie;
import com.xebiatest.boxotop.app.model.Review;
import com.xebiatest.boxotop.app.ui.base.BaseActivity;
import com.xebiatest.boxotop.app.ui.base.BaseFragment;
import com.xebiatest.boxotop.app.ui.view.MovieDetailView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by pierrecastex on 18/09/2014.
 * Fragment that describes the movie details
 */
public class MovieDetailFragment extends BaseFragment {

    public static final String ARG_ITEM_ID = "item_id";

    //CustomView with binding method to movie model
    private MovieDetailView movieDetailView;

    private Movie movie;

    private long movie_id;

    @Inject
    JobManager jobManager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view =  inflater.inflate(R.layout.item_detail_fragment, container,false);
        ButterKnife.inject(this, view);
        movieDetailView = new MovieDetailView(getActivity());
        return movieDetailView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState!=null){
            movie = savedInstanceState.getParcelable("movie");
            movie_id = savedInstanceState.getLong("movie_id");
            if(movie!=null) {
                updateMovie();
            }else{
                movieDetailView.setOnNetworkErrorLayout(true);
            }
        }else {
            movie_id = getArguments().getLong(ARG_ITEM_ID);
            fetchMovie(movie_id);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("movie", movie);
        outState.putLong("movie_id", movie_id);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity) getActivity()).setActionBar(null, true);
    }

    private void fetchMovie(long movieId){
        jobManager.addJobInBackground(new FetchMovieJob( movieId));
    }

    public void onEventMainThread(FetchMovieEvent event) {
       movie = event.getMovie();
       updateMovie();
    }
    public void onEventMainThread(AddJobEvent event) {
        fetchMovie(movie_id);
    }
    public void onEventMainThread(NetworkErrorEvent event) {
        if(event.getErrorType()==NetworkErrorEvent.OTHER) {
            movieDetailView.setOnNetworkErrorLayout(true);
        }
    }

    private void updateMovie(){
        movieDetailView.bindModel(movie);
        movieDetailView.setupListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieDetailView.enableRating(false);
                showEditDialog();
            }
        });
        Review userReview = (Review) BoxotopApp.getInstance().getObjectFromSharedPreferences(Review.class, movie.getTitle());
        if(userReview!=null) {
            movieDetailView.setUserReview(userReview.getReview());
            movieDetailView.setUserRating(userReview.getRating());
        }

    }

    public void onEventMainThread(SaveCommentEvent event) {
        if(event.getComment()!=null) {
            movieDetailView.setUserReview(event.getComment());
            movieDetailView.setUserRating(event.getRatingStars());
            Review review = new Review();
            review.setRating(event.getRatingStars());
            review.setReview(event.getComment());
            BoxotopApp.getInstance().saveObjectInPreferences(review, movie.getTitle());
        }
        movieDetailView.enableRating(true);

    }


    private void showEditDialog() {
        RatingDialog editNameDialog = RatingDialog.newInstance(getString(R.string.rating_dialog_title), movieDetailView.getUserRating(),movieDetailView.getUserReview());
        editNameDialog.show(((ActionBarActivity)getActivity()).getSupportFragmentManager(), RatingDialog.TAG);
    }

}
