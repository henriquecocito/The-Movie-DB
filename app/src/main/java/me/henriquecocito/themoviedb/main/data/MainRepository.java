package me.henriquecocito.themoviedb.main.data;

import me.henriquecocito.themoviedb.base.data.BaseRepository;
import me.henriquecocito.themoviedb.main.MainContract;
import me.henriquecocito.themoviedb.main.data.model.GenresResponse;
import retrofit2.Call;

public class MainRepository extends BaseRepository implements MainContract.Repository {

    @Override
    public Call<GenresResponse> getGenres() {
        return getAPI(MainDataSource.class).getGenres();
    }
}
