package com.xebiatest.boxotop.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import dagger.ObjectGraph;

/**
 * Created by pierrecastex on 20/09/2014.
 */
public class BoxotopApp extends Application {

    private static BoxotopApp instance;

    private int cacheIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        getObjectGraph();
        cacheIntent=0;
    }

    public BoxotopApp() {
        instance = this;
    }

    public static BoxotopApp getInstance() {
        return instance;
    }

    private ObjectGraph objectGraph;

    public synchronized ObjectGraph getObjectGraph() {
        objectGraph = ObjectGraph.create(new BoxotopModule());
        return objectGraph;
    }

    public static void injectMembers(Object object) {
        getInstance().objectGraph.inject(object);
    }



    /**
     * Temporary used for the user personal reviews.(see {@link com.xebiatest.boxotop.app.model.Review})
     * Avoid to use in future developments
    **/
    @Deprecated
    public void saveObjectInPreferences(Object object,String tag){
        SharedPreferences.Editor prefsEditor = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        prefsEditor.putString(tag, json);
        prefsEditor.commit();

    }
    @Deprecated
    public Object getObjectFromSharedPreferences(Class clazz,String tag ){
        Gson gson = new Gson();
        String json = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE).getString(tag, "");
        return gson.fromJson(json, clazz);

    }

    /**
     * Cache toast every five times the user wants to refresh the data
     */
    public boolean showCacheMessageError(){
        if((cacheIntent%5)==0){
            cacheIntent++;
            return true;

        }else{
            cacheIntent++;
            return false;
        }
    }
}
