package me.henriquecocito.themoviedb.detail.data;

import me.henriquecocito.themoviedb.detail.data.model.DetailResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface DetailDataSource {

    @GET("movie/{movie}")
    Call<DetailResponse> getDetail(@Path(value = "movie", encoded = true) int id);
}
