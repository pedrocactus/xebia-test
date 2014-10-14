package com.xebiatest.boxotop.app.service;

import com.xebiatest.boxotop.app.model.MoviesWrapper;
import com.xebiatest.boxotop.app.model.Movie;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by pierrecastex on 20/09/2014.
 */
public interface BoxotopApiService {

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/api/public/v1.0/lists/movies/box_office.json")
    MoviesWrapper getMovieList();

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/api/public/v1.0/movies/{id}.json")
    Movie getMovie(@Path("id") long movieId);

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("/api/public/v1.0/movies/{id}/similar.json")
    MoviesWrapper getSimilarMovies(@Path("id") long movieId);
}
