package com.xebiatest.boxotop.app;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;
import com.path.android.jobqueue.network.NetworkUtilImpl;
import com.squareup.okhttp.HttpResponseCache;
import com.squareup.okhttp.OkHttpClient;
import com.xebiatest.boxotop.Config;
import com.xebiatest.boxotop.app.Job.FetchMovieJob;
import com.xebiatest.boxotop.app.Job.FetchMoviesJob;
import com.xebiatest.boxotop.app.events.NetworkErrorEvent;
import com.xebiatest.boxotop.app.service.BoxotopApiService;
import com.xebiatest.boxotop.app.ui.DetailActivity;
import com.xebiatest.boxotop.app.ui.MainActivity;
import com.xebiatest.boxotop.app.ui.MovieDetailFragment;
import com.xebiatest.boxotop.app.ui.MovieListFragment;
import com.xebiatest.boxotop.app.ui.RatingDialog;
import com.xebiatest.boxotop.app.ui.utils.JacksonConverter;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by pierrecastex on 20/09/2014.
 */

@Module(
        injects = {
                MainActivity.class,
                DetailActivity.class,
                MovieListFragment.class,
                MovieDetailFragment.class,
                RatingDialog.class,
                FetchMoviesJob.class,
                FetchMovieJob.class,
                BoxotopApp.class,
                JobManager.class
        }
)
public class BoxotopModule {



    @Provides
    @Singleton
    de.greenrobot.event.EventBus provideEventBus() {
        return de.greenrobot.event.EventBus.getDefault();
    }


    @Provides
    @Singleton
    JobManager provideJobManager() {
        Configuration configuration = new Configuration.Builder(BoxotopApp.getInstance())
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .build();
        return new JobManager(BoxotopApp.getInstance(), configuration);
    }

    @Provides
    BoxotopApiService provideApiClient() {

    RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Config.PRODUCTION_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new JacksonConverter(new ObjectMapper()));

	//Implementing cache for retrofit calls
	//Simple cache strategy with caching reading when the network is not available
	//(cache memory up to 10 mb 
    OkHttpClient okHttpClient = new OkHttpClient();
    File cacheDir = BoxotopApp.getInstance().getCacheDir();
    HttpResponseCache cache = null;
    try {
        cache = new HttpResponseCache(cacheDir, 10 * 1024 * 1024);
    } catch (IOException e) {
        e.printStackTrace();
    }
    okHttpClient.setResponseCache(cache);
    builder.setClient(new OkClient(okHttpClient));

	//Adding Interceptor for cache request only behaviors 
    builder.setRequestInterceptor(new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            NetworkUtilImpl util = new NetworkUtilImpl(BoxotopApp.getInstance());
            if (util.isConnected(BoxotopApp.getInstance())) {
                int maxAge = 0;
                request.addHeader("Cache-Control", "public, max-age=" + maxAge);
            } else {
            de.greenrobot.event.EventBus.getDefault().post(new NetworkErrorEvent(NetworkErrorEvent.CACHE));
                int maxStale = 60 * 60 * 24 * 8; // tolerate 8 days stale
                request.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
        }
    });
    return builder.build().create(BoxotopApiService.class);
    }
}
