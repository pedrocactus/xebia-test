package com.xebiatest.boxotop.app.Job;

import android.content.Context;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.xebiatest.boxotop.app.BoxotopApp;
import com.xebiatest.boxotop.app.events.NetworkErrorEvent;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by pierrecastex on 29/09/2014.
 */
public abstract class BaseNetworkJob extends Job {
    public static final int PRIORITY = 1;

    @Inject
    protected EventBus eventBus;

    protected BaseNetworkJob() {
        super(new Params(PRIORITY));
        BoxotopApp.injectMembers(BoxotopApp.getInstance());
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        eventBus.post(new NetworkErrorEvent(NetworkErrorEvent.OTHER));
        return false;
    }
}
