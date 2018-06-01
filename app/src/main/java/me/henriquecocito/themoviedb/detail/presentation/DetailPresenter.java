package me.henriquecocito.themoviedb.detail.presentation;

import me.henriquecocito.themoviedb.base.data.model.ErrorMapper;
import me.henriquecocito.themoviedb.base.data.model.ErrorResponse;
import me.henriquecocito.themoviedb.detail.DetailContract;
import me.henriquecocito.themoviedb.detail.domain.DetailInteractor;
import me.henriquecocito.themoviedb.detail.data.model.DetailResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.Domain interactor = new DetailInteractor();
    private DetailContract.View view;

    public DetailPresenter(DetailContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void load(int id) {
        view.showLoading();
        interactor.loadDetail(id, new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if(response.isSuccessful()) {
                    view.showDetail(response.body());
                    view.hideLoading();
                } else {
                    ErrorResponse errorResponse = ErrorMapper.parse(response);
                    onFailure(call, new Throwable(errorResponse.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                view.showError(t);
                view.hideLoading();
            }
        });
    }
}
