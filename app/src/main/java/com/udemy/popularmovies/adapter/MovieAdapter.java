package com.udemy.popularmovies.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udemy.popularmovies.R;
import com.udemy.popularmovies.data.Movie;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.udemy.popularmovies.utils.NetworkUtils.KEY;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private final MovieAdapterOnClickHandler mClickHandler;
    private List<Movie> movies;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = View.inflate(viewGroup.getContext(), R.layout.movie_item, null);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        setMoviePostersOnline(movieAdapterViewHolder, position);
    }

    private void setMoviePostersOnline(final MovieAdapterViewHolder movieAdapterViewHolder, final int position) {
        String mMoviePosterPath = movies.get(position).getPosterPath();

        String path = "https://image.tmdb.org/t/p/w780".concat(mMoviePosterPath);
        Log.d(TAG, path);
        new Picasso.Builder(movieAdapterViewHolder.itemView.getContext())
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        exception.printStackTrace();
                    }
                })
                .build()
                .load(path)
                .into(movieAdapterViewHolder.getPosterimageview());
    }

    @Override
    public int getItemCount() {
        if (null == movies) return 0;
        return movies.size();
    }

    public void setMoviesData(List<Movie> movies) {
        this.movies = movies;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView posterimageview;


        public MovieAdapterViewHolder(View view) {
            super(view);
            posterimageview = view.findViewById(R.id.moviePoster);
            view.setOnClickListener(this);
        }

        public ImageView getPosterimageview() {
            return posterimageview;
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(movies.get(adapterPosition));
        }
    }
}
