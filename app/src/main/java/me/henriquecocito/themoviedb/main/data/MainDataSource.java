package me.henriquecocito.themoviedb.main.data;

import me.henriquecocito.themoviedb.main.data.model.GenresResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MainDataSource {

    @GET("genre/movie/list")
    Call<GenresResponse> getGenres();
}
