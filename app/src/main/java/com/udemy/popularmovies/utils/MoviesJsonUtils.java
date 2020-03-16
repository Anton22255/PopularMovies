package com.udemy.popularmovies.utils;

import com.udemy.popularmovies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class MoviesJsonUtils {

    public static List<Movie> getMoviesStringsFromJson(String moviesJsonStr) throws JSONException {

        final String MOVIE_LIST = "results";
        final String MOVIE_ID = "id";
        final String TITLE = "title";
        final String POSTER = "poster_path";
        final String BACKDROP = "backdrop_path";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";

        List<Movie> movies = new ArrayList<>();

        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        JSONArray movieArray = moviesJson.getJSONArray(MOVIE_LIST);

        for (int i = 0; i < movieArray.length(); i++) {

            JSONObject movieJson = movieArray.getJSONObject(i);

            String id = movieJson.getString(MOVIE_ID);
            String title = movieJson.getString(TITLE);
            String poster = movieJson.getString(POSTER);
            String backdrop = movieJson.getString(BACKDROP);
            String userRating = movieJson.getString(VOTE_AVERAGE);
            String releaseDate = movieJson.getString(RELEASE_DATE);
            String overview = movieJson.getString(OVERVIEW);

            Movie movie = new Movie(id, title, poster, backdrop, userRating, releaseDate, overview);
            movies.add(movie);
        }
        return movies;
    }
}
