package me.henriquecocito.themoviedb.movie.data;

import me.henriquecocito.themoviedb.movie.data.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDataSource {

    @GET("genre/{genre_id}/movies")
    Call<MovieResponse> getMovies(@Path(value = "genre_id", encoded = true) int genreId, @Query("page") int page);
}
