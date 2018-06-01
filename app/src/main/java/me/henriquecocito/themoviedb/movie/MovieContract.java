package me.henriquecocito.themoviedb.movie;

import java.util.List;

import me.henriquecocito.themoviedb.base.BaseContract;
import me.henriquecocito.themoviedb.movie.data.model.Movie;
import me.henriquecocito.themoviedb.movie.data.model.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;

public interface MovieContract {

    interface View extends BaseContract.View {
        void openMovie(Movie movie);
        void showItems(List<Movie> movies);
        void showEmptyView();
        void hideEmptyView();
        void hideErrorView();
    }

    interface Presenter extends BaseContract.Presenter {
        void load(int genreId);
        void reset();
    }

    interface Domain {
        void loadMovies(int genreId, int page, Callback<MovieResponse> callback);
    }

    interface Repository {
        Call<MovieResponse> getMovies(int genreId, int page);
    }
}
