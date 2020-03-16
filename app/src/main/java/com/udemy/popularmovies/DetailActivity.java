package com.udemy.popularmovies;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udemy.popularmovies.data.Movie;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private Movie mMovie;
    private ImageView backdrop;
    private ImageView posterThumbnail;
    private TextView rating;
    private TextView dateReleased;
    private TextView overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        backdrop = findViewById(R.id.movieBackdrop);
        posterThumbnail = findViewById(R.id.moviePosterThumbnail);
        rating = findViewById(R.id.rating);
        dateReleased = findViewById(R.id.releaseDate);
        overview = findViewById(R.id.overview);

        mMovie = (Movie) getIntent().getExtras().getSerializable("movie");

        Picasso.get().load("https://image.tmdb.org/t/p/w1280".concat(mMovie.getBackdropPath())).into(backdrop);
        Picasso.get().load("https://image.tmdb.org/t/p/w500".concat(mMovie.getPosterPath())).into(posterThumbnail);

        setTitle(mMovie.getTitle());
        String userRating = mMovie.getUserRating();
        String newUserRating = userRating + "/10";

        rating.setText(newUserRating);
        dateReleased.setText(mMovie.getReleaseDate());
        overview.setText(mMovie.getOverview());
    }
}