package me.henriquecocito.themoviedb.movie.domain;

import me.henriquecocito.themoviedb.movie.MovieContract;
import me.henriquecocito.themoviedb.movie.data.MovieRepository;
import me.henriquecocito.themoviedb.movie.data.model.MovieResponse;
import retrofit2.Callback;

public class MovieInteractor implements MovieContract.Domain {

    private MovieContract.Repository repository = new MovieRepository();

    @Override
    public void loadMovies(int genreId, int page, Callback<MovieResponse> callback) {
        repository.getMovies(genreId, page).enqueue(callback);
    }
}
