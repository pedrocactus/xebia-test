package com.xebiatest.boxotop.app.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.xebiatest.boxotop.app.Job.FetchMoviesJob;
import com.xebiatest.boxotop.app.R;
import com.xebiatest.boxotop.app.events.ActionBarConfigEvent;
import com.xebiatest.boxotop.app.events.AddJobEvent;
import com.xebiatest.boxotop.app.events.FetchMovieListEvent;
import com.xebiatest.boxotop.app.events.NavigationToDetailEvent;
import com.xebiatest.boxotop.app.events.NetworkErrorEvent;
import com.xebiatest.boxotop.app.events.SearchFilterEvent;
import com.xebiatest.boxotop.app.model.Movie;
import com.xebiatest.boxotop.app.ui.base.BaseActivity;

import java.util.List;

/**
 * Created by pierrecastex on 17/09/2014.
 */
public class MainActivity extends BaseActivity implements android.support.v7.widget.SearchView.OnQueryTextListener {

    private Menu optionsMenu;

    private static final String SEARCH_QUERY = "search_text";

    private CharSequence queryText;

    private SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_layout);
        if(savedInstanceState==null)
        initFragments();
    }

    private void initFragments() {

        MovieListFragment movieListFragment = new MovieListFragment();

        movieListFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, movieListFragment).commit();

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(SEARCH_QUERY)) {
            queryText = savedInstanceState.getCharSequence(SEARCH_QUERY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.optionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_list_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if(queryText!=null) {
            searchView.setIconified(false);
            if(android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB) {
                searchItem.expandActionView();
            }
            searchView.setQuery(queryText, true);


        }

        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                eventBus.post(new AddJobEvent());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToDetailFragment(long movieId){

            Intent detailIntent = new Intent(this, DetailActivity.class);
            detailIntent.putExtra(MovieDetailFragment.ARG_ITEM_ID, movieId);
            startActivity(detailIntent);

    }

    public void onEventMainThread(NavigationToDetailEvent event) {
        goToDetailFragment(event.getId());
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText))
        {
            eventBus.post(new SearchFilterEvent(SearchFilterEvent.CLEAR,null));
            queryText = null;
        }
        else
        {
            eventBus.post(new SearchFilterEvent(SearchFilterEvent.FILTER, newText));
            queryText = newText;
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence(SEARCH_QUERY, queryText);
        super.onSaveInstanceState(outState);
    }
}
