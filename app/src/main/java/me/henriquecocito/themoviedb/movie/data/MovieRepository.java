package me.henriquecocito.themoviedb.movie.data;

import me.henriquecocito.themoviedb.base.data.BaseRepository;
import me.henriquecocito.themoviedb.movie.MovieContract;
import me.henriquecocito.themoviedb.movie.data.model.MovieResponse;
import retrofit2.Call;

public class MovieRepository extends BaseRepository implements MovieContract.Repository {

    @Override
    public Call<MovieResponse> getMovies(int genreId, int page) {
        return getAPI(MovieDataSource.class).getMovies(genreId, page);
    }
}
