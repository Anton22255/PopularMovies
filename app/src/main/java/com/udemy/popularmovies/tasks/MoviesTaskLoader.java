package com.udemy.popularmovies.tasks;

import android.content.Context;

import com.udemy.popularmovies.data.Movie;
import com.udemy.popularmovies.utils.MoviesJsonUtils;
import com.udemy.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

public class MoviesTaskLoader extends AsyncTaskLoader<List<Movie>> {
    private static final String TAG = MoviesTaskLoader.class.getSimpleName();
    public static final int ID = 101;
    private final String mFilter;
    List<Movie> mGithubJson;

    public MoviesTaskLoader(@NonNull Context context, String filter) {
        super(context);
        mFilter = filter;
    }

    @Override
    protected void onStartLoading() {
        if (mGithubJson != null) {
            deliverResult(mGithubJson);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Movie> loadInBackground() {
        if (mFilter == null) {
            return null;
        }
        URL moviesRequestUrl = NetworkUtils.buildURL("movie", mFilter);
        try {
            String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);

            List<Movie> movieJsonData = MoviesJsonUtils.getMoviesStringsFromJson(jsonMovieResponse);

            return movieJsonData;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void deliverResult(List<Movie> githubJson) {
        mGithubJson = githubJson;
        super.deliverResult(githubJson);
    }
}