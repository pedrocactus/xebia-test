package com.xebiatest.boxotop.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.path.android.jobqueue.JobManager;
import com.xebiatest.boxotop.app.BoxotopApp;
import com.xebiatest.boxotop.app.Job.FetchMoviesJob;
import com.xebiatest.boxotop.app.R;
import com.xebiatest.boxotop.app.events.AddJobEvent;
import com.xebiatest.boxotop.app.events.FetchMovieListEvent;
import com.xebiatest.boxotop.app.events.NavigationToDetailEvent;
import com.xebiatest.boxotop.app.events.NetworkErrorEvent;
import com.xebiatest.boxotop.app.events.SearchFilterEvent;
import com.xebiatest.boxotop.app.model.Movie;
import com.xebiatest.boxotop.app.ui.base.BaseActivity;
import com.xebiatest.boxotop.app.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

/**
 * Created by pierrecastex on 17/09/2014.
 * Fragment the list the BoxOffice movies
 */
public class MovieListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    @Inject
    JobManager jobManager;

    @InjectView(R.id.movie_listview)
    ListView listView;

    @InjectView(R.id.no_network_layout)
    RelativeLayout noNetworkLayout;

    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    private MovieListAdapter adapter;

    private ArrayList<Movie> movies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = super.onCreateView(inflater,container,savedInstanceState);
        View view =  inflater.inflate(R.layout.item_list_fragment, container,false);
        ButterKnife.inject(this, view);
        adapter = new MovieListAdapter(this.getActivity());
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_green_light, android.R.color.holo_green_dark, android.R.color.holo_green_dark, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies",movies);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState!=null){
            movies = savedInstanceState.getParcelableArrayList("movies");
            updateList();
        }else {
            fetchMovies();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity) getActivity()).setActionBar(getString(R.string.ab_title_boxoffice),false);
    }

    private void fetchMovies(){
        jobManager.addJobInBackground(new FetchMoviesJob());
    }


    public void onEventMainThread(FetchMovieListEvent event) {
        movies = (ArrayList<Movie>) event.getMovies();
        updateList();

    }
    private void updateList(){
        noNetworkLayout.setVisibility(View.GONE);
        ((MovieListAdapter)listView.getAdapter()).updateMovies(movies);
        swipeRefreshLayout.setRefreshing(false);
    }
    public void onEventMainThread(NetworkErrorEvent event) {
        if(event.getErrorType()==NetworkErrorEvent.CACHE) {
            if(BoxotopApp.getInstance().showCacheMessageError())
                Toast.makeText(BoxotopApp.getInstance(), getString(R.string.no_network_cache), Toast.LENGTH_SHORT).show();
        }else{
            noNetworkLayout.setVisibility(View.VISIBLE);
        }

    }

    public void onEventMainThread(AddJobEvent event) {
        swipeRefreshLayout.setRefreshing(true);
        fetchMovies();
    }

    public void onEventMainThread(SearchFilterEvent event) {
        if(event.getAction().equals(SearchFilterEvent.FILTER)) {

            listView.setFilterText(event.getText());
        }else{
            listView.clearTextFilter();
        }
    }

    @Override
    public void onRefresh() {
        fetchMovies();
    }

    @OnItemClick(R.id.movie_listview)
    public void onMovieClicked(int position){
        eventBus.post(new NavigationToDetailEvent(adapter.getItem(position).getId()));
    }

    @OnClick(R.id.retry_button)
    public void onRetryButtonClicke(){
        fetchMovies();
    }
}
