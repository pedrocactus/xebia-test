package com.xebiatest.boxotop.app.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xebiatest.boxotop.app.R;
import com.xebiatest.boxotop.app.events.AddJobEvent;
import com.xebiatest.boxotop.app.model.Movie;
import com.xebiatest.boxotop.app.ui.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by pierrecastex on 30/09/2014.
 * View which you can bind a movie model
 */
public class MovieDetailView extends LinearLayout {

    @InjectView(R.id.movie_imageview)
    ImageView movieImage;

    @InjectView(R.id.title_detail_textview)
    TextView movieTitle;

    @InjectView(R.id.ratingbar_fragment)
    RatingBar ratingBar;

    @InjectView(R.id.ratingbar_fragment_layout)
    FrameLayout ratingBarLayout;

    @InjectView(R.id.detail_myreview_body)
    TextView myReviewBody;

    @InjectView(R.id.detail_release_date_textview)
    TextView releaseDateTextView;

    @InjectView(R.id.crtitics_textview)
    TextView criticsTextView;

    @InjectView(R.id.crtitics_ratingbar)
    RatingBar criticsRatingBar;

    @InjectView(R.id.audience_textview)
    TextView audienceTextView;

    @InjectView(R.id.audience_ratingBar)
    RatingBar audienceRatingBar;

    RelativeLayout noNetworkLayout;

    Button retryButton;
    
    SwipeRefreshLayout swipeRefreshLayout;
    
    ListView movieDescriptionListView;

    private DetaiListAdapter adapter;


    public MovieDetailView(Context context) {
        super(context);
        View headerView = LayoutInflater.from(context).inflate(R.layout.item_detail_fragment, null, true);
        LayoutInflater.from(context).inflate(R.layout.list_detail_info, this, true);

        ButterKnife.inject(this, headerView);
        movieDescriptionListView = (ListView)findViewById(R.id.listView_detail_info);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_green_light, android.R.color.holo_green_dark, android.R.color.holo_green_dark, android.R.color.holo_green_light);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setEnabled(false);
        movieDescriptionListView.addHeaderView(headerView);
        adapter = new DetaiListAdapter(context);
        movieDescriptionListView.setAdapter(adapter);
        movieDescriptionListView.setOnItemClickListener(null);
        movieDescriptionListView.setVisibility(GONE);
        noNetworkLayout = (RelativeLayout)findViewById(R.id.no_network_layout);
        retryButton = (Button)findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new AddJobEvent());
            }
        });

    }

    public void bindModel(Movie movie){
        noNetworkLayout.setVisibility(View.GONE);
        movieDescriptionListView.setVisibility(VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        movieTitle.setText(movie.getTitle());
        Picasso.with(getContext())
                .load(movie.getPosters().getThumbnail())
                .into(movieImage);
	    DateFormat format = new SimpleDateFormat(getContext().getString(R.string.date_format_classic));
        releaseDateTextView.setText(getContext().getString(R.string.detail_rating_release_date_title) + format.format(movie.getReleaseDates().getTheater()));

        criticsTextView.setText(getContext().getString(R.string.detail_rating_critics_title));
        criticsRatingBar.setRating(Utils.gradeToRate(movie.getRatings().getCriticsScrore(), Utils.SCORE_BASE));
        audienceTextView.setText(getContext().getString(R.string.detail_rating_audience_title));
        audienceRatingBar.setRating(Utils.gradeToRate(movie.getRatings().getAudienceScore(), Utils.SCORE_BASE));

        //indicator mode workaround for custom ratingbar (known issue)
        criticsRatingBar.setOnClickListener(null);
        audienceRatingBar.setOnClickListener(null);
        ratingBar.setOnClickListener(null);

        //Setting up the listview with different layotus
        ArrayList<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

        if(movie.getSynopsis().length()!=0)
            detailList.add(getDetailItemInfo(DetaiListAdapter.DetailType.SYNOPSIS,getContext().getString(R.string.detail_synopsis),movie.getSynopsis()));

        if(movie.getActors()!=null&&movie.getActors().size()!=0)
            detailList.add(getDetailItemInfo(DetaiListAdapter.DetailType.CAST,getContext().getString(R.string.detail_casting),movie.getActors()));

        if(movie.getSimilarMovies()!=null&&movie.getSimilarMovies().size()!=0)
        detailList.add(getDetailItemInfo(DetaiListAdapter.DetailType.SIMILAR_MOVIES,getContext().getString(R.string.detail_similar_movies),movie.getSimilarMovies()));

        adapter.putData(detailList);
        ratingBarLayout.setClickable(true);
    }

    private HashMap<String, Object> getDetailItemInfo(DetaiListAdapter.DetailType type,String title, Object body) {
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put(DetaiListAdapter.TYPE, type);
        item.put(DetaiListAdapter.TITLE, title);
        item.put(DetaiListAdapter.BODY, body);
        return item;
    }

    public void setUserReview(String review){
        if(review.length()!=0) {
            myReviewBody.setVisibility(VISIBLE);
            myReviewBody.setText(Utils.textToQuote(review));
        }else{
            myReviewBody.setVisibility(GONE);
        }
    }
    public void setUserRating(float grade){
        ratingBar.setRating(Utils.gradeToRate(grade, Utils.RATE_BASE));

    }
    public float getUserRating(){
       return ratingBar.getRating();
    }
    public String getUserReview(){
        return myReviewBody.getText().toString();
    }

    public void enableRating(boolean enable){

        ratingBarLayout.setClickable(enable);
    }

    public void setupListener(OnClickListener listener){
        ratingBarLayout.setOnClickListener(listener);
    }

    public void setOnNetworkErrorLayout(boolean enable){
        if(enable) {
            noNetworkLayout.setVisibility(VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }else{
            noNetworkLayout.setVisibility(GONE);

        }

    }


}
