package com.xebiatest.boxotop.app.ui.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.xebiatest.boxotop.app.BoxotopApp;
import com.xebiatest.boxotop.app.events.ActionBarConfigEvent;
import com.xebiatest.boxotop.app.events.NavigationToDetailEvent;
import com.xebiatest.boxotop.app.service.BoxotopApiService;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by pierrecastex on 27/09/2014.
 */
public class BaseActivity extends ActionBarActivity {

    @Inject
    protected EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BoxotopApp.injectMembers(this);
    }

    public void setActionBar(String title, boolean isEnable){

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isEnable);

    }
    @Override
    protected void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }
}
