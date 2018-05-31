package me.henriquecocito.themoviedb.main.domain;

import me.henriquecocito.themoviedb.main.MainContract;
import me.henriquecocito.themoviedb.main.data.MainRepository;
import me.henriquecocito.themoviedb.main.data.model.GenresResponse;
import retrofit2.Callback;

public class MainInteractor implements MainContract.Domain {

    private MainContract.Repository repository = new MainRepository();

    @Override
    public void loadGenres(Callback<GenresResponse> callback) {
        repository.getGenres().enqueue(callback);
    }
}
