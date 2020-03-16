package com.udemy.popularmovies.data;

import java.io.Serializable;

public class Movie implements Serializable {

    private String title;
    private String id;
    private String posterPath;
    private String backdrop;
    private String userRating;
    private String releaseDate;
    private String overview;

    public Movie(String id, String title, String posterPath, String backdrop, String userRating, String releaseDate, String overview) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.backdrop = backdrop;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.overview = overview;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdrop;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }
}