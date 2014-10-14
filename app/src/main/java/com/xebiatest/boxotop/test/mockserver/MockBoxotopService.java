package com.xebiatest.boxotop.test.mockserver;

import com.xebiatest.boxotop.app.model.Actor;
import com.xebiatest.boxotop.app.model.MoviesWrapper;
import com.xebiatest.boxotop.app.model.Movie;
import com.xebiatest.boxotop.app.model.Posters;
import com.xebiatest.boxotop.app.model.Ratings;
import com.xebiatest.boxotop.app.model.ReleaseDates;
import com.xebiatest.boxotop.app.service.BoxotopApiService;

import java.util.ArrayList;
import java.util.Date;

import retrofit.http.Path;

/**
 * Created by pierrecastex on 23/09/2014.
 */
public class MockBoxotopService implements BoxotopApiService{
    MoviesWrapper boxOffice;

    public MockBoxotopService() {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        ArrayList<Actor> actors = new ArrayList<Actor>();
        movies.add(new Movie(232422323,"Invictus", 8908,"foo",actors,"foo",new Posters("http://content7.flixster.com/movie/10/92/27/10922777_mob.jpg","http://content7.flixster.com/movie/10/92/27/10922777_pro.jpg","http://content7.flixster.com/movie/10/92/27/10922777_det.jpg","http://content7.flixster.com/movie/10/92/27/10922777_ori.jpg"),new ReleaseDates(new Date(), new Date()),new Ratings("critics ratings",43,"audience rating",54),null));
        boxOffice = new MoviesWrapper();
        boxOffice.setMovies(movies);
    }

    @Override
    public MoviesWrapper getMovieList() {
        return boxOffice;

    }

    @Override
    public Movie getMovie(long movieId) {
        return boxOffice.getMovies().get(0);
    }

    @Override
    public MoviesWrapper getSimilarMovies(@Path("id") long movieId) {
        return boxOffice;
    }
}
