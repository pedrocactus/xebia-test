package com.xebiatest.boxotop.app.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xebiatest.boxotop.app.R;
import com.xebiatest.boxotop.app.model.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pierrecastex on 18/09/2014.
 */
public class MovieListAdapter extends BaseAdapter implements Filterable {

    private List<Movie> movies;
    private List<Movie> moviesTmpSearch;
    private final Context context;

    public MovieListAdapter(Context context){
        this.context = context;
        this.movies = new ArrayList<Movie>();
        this.moviesTmpSearch = new ArrayList<Movie>();

    }

    @Override
    public int getCount() {
        return moviesTmpSearch.size();
    }

    @Override
    public Movie getItem(int position) {
        return moviesTmpSearch.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.movie_list_item, parent, false);
        }
        ImageView movieThumbNailView = (ImageView) ViewHolder.get(convertView,R.id.movie_thumbnail);
        TextView movieTitleView = ViewHolder.get(convertView,R.id.movie_textview);
        Movie movie = getItem(position);
        movieTitleView.setText(movie.getTitle());
        Picasso.with(context)
                .load(movie.getPosters().getThumbnail())
                .placeholder(R.drawable.placeholder_movie)
                .into(movieThumbNailView);

        return convertView;
    }

    public void updateMovies(List<Movie> movies) {
        //this.movies = movies;
        this.movies.clear();
        this.movies.addAll(movies);
        this.moviesTmpSearch.clear();
        this.moviesTmpSearch.addAll(movies);
        notifyDataSetChanged();
    }

    public void updateMovie(final int position, final Movie movie) {

        this.movies.add(position,movie);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                    notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    // No filter implemented we return all the list
                    moviesTmpSearch.clear();
                    moviesTmpSearch.addAll(movies);
                }
                else {
                    moviesTmpSearch.clear();
                    for (Movie movie : movies) {
                        if (movie.getTitle().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                            moviesTmpSearch.add(movie);
                    }

                }
                return results;
            }
        };

        return filter;
    }
}
