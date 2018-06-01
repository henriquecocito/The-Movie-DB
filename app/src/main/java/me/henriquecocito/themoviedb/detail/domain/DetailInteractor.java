package me.henriquecocito.themoviedb.detail.domain;

import me.henriquecocito.themoviedb.detail.DetailContract;
import me.henriquecocito.themoviedb.detail.data.DetailRepository;
import me.henriquecocito.themoviedb.detail.data.model.DetailResponse;
import retrofit2.Callback;

public class DetailInteractor implements DetailContract.Domain {

    private DetailContract.Repository repository = new DetailRepository();

    @Override
    public void loadDetail(int id, Callback<DetailResponse> callback) {
        repository.getDetail(id).enqueue(callback);
    }
}
