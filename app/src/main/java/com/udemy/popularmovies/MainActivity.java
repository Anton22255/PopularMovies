package com.udemy.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.udemy.popularmovies.adapter.MovieAdapter;
import com.udemy.popularmovies.data.Movie;
import com.udemy.popularmovies.tasks.MoviesTaskLoader;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final int MOVIE_LOADER = 11;
    private static final int TOP_RATED_LOADER = 22;
    private static final String FILTER_STATE = "filter";
    private static final String RECYCLER_POSITION = "RecyclerViewPosition";

    private MovieAdapter movieAdapter;
    private View loadingIndicator;
    private RecyclerView recyclerView;

    private String mFilterState = "popular";
    private Parcelable recyclerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.moviesContainer);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);

        loadingIndicator = findViewById(R.id.loadingIndicator);

        getSupportLoaderManager().initLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public void onClick(Movie movie) {
        Intent movieDetailIntent = new Intent(MainActivity.this, DetailActivity.class);
        movieDetailIntent.putExtra("movie", movie);
        startActivity(movieDetailIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set_popular:
                loadMovieData("popular");
                mFilterState = "popular";
                setActionBarTitle(mFilterState);
                return true;
            case R.id.set_trending:
                loadMovieData("top_rated");
                mFilterState = "top_rated";
                setActionBarTitle(mFilterState);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showLoadingIndicator() {
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    public void hideLoadingIndicator() {
        loadingIndicator.setVisibility(View.INVISIBLE);
    }

    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        showLoadingIndicator();
        if (id == MOVIE_LOADER) {
            return new MoviesTaskLoader(this, "popular");
        } else {
            return new MoviesTaskLoader(this, "top_rated");
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> data) {
        hideLoadingIndicator();
        setMoviePosters(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(FILTER_STATE, mFilterState);
        outState.putParcelable(RECYCLER_POSITION,
                recyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mFilterState = savedInstanceState.getString(FILTER_STATE);
        recyclerPosition = savedInstanceState.getParcelable(RECYCLER_POSITION);

        if (mFilterState.equals("top_rated")) {
            loadMovieData("top_rated");
        } else {
            loadMovieData("popular");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerPosition != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerPosition);
        }
    }

    public void setMoviePosters(List<Movie> movieData) {
        movieAdapter.setMoviesData(movieData);
        movieAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
        if (recyclerPosition != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerPosition);
        }
    }

    public void setActionBarTitle(String title) {
        switch (title) {
            case "popular":
                setTitle("Popular");
                break;
            case "top_rated":
                setTitle("Top Rated");
                break;
        }
    }

    private void loadMovieData(String filter) {
        if (filter.equals("top_rated")) {
            getSupportLoaderManager().initLoader(TOP_RATED_LOADER, null, this);
        } else {
            getSupportLoaderManager().initLoader(MOVIE_LOADER, null, this);
        }
    }
}