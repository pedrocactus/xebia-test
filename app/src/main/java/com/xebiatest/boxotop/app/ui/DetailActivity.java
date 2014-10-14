package com.xebiatest.boxotop.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.xebiatest.boxotop.app.R;
import com.xebiatest.boxotop.app.events.ActionBarConfigEvent;
import com.xebiatest.boxotop.app.events.NavigationToDetailEvent;
import com.xebiatest.boxotop.app.ui.base.BaseActivity;

/**
 * Created by pierrecastex on 27/09/2014.
 */
public class DetailActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putLong(MovieDetailFragment.ARG_ITEM_ID,
                    getIntent().getLongExtra(MovieDetailFragment.ARG_ITEM_ID,-1));
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }

        initActionBar();
    }

    private void initActionBar(){

        eventBus.post(new ActionBarConfigEvent(null,false));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEventMainThread(NavigationToDetailEvent event) {
    }
}
