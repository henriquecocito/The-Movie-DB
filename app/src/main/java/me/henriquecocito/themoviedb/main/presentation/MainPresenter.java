package me.henriquecocito.themoviedb.main.presentation;

import me.henriquecocito.themoviedb.base.data.model.ErrorMapper;
import me.henriquecocito.themoviedb.base.data.model.ErrorResponse;
import me.henriquecocito.themoviedb.main.MainContract;
import me.henriquecocito.themoviedb.main.data.model.GenresResponse;
import me.henriquecocito.themoviedb.main.domain.MainInteractor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private MainContract.Domain interactor = new MainInteractor();

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    public MainPresenter(MainContract.View view, MainContract.Domain interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void start() {
        load();
    }

    @Override
    public void load() {
        view.showLoading();
        clearViews();
        interactor.loadGenres(new Callback<GenresResponse>() {
            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getGenres().size() > 0)
                        view.showItems(response.body().getGenres());
                    else
                        view.showEmptyView();

                    view.hideLoading();
                } else {
                    ErrorResponse errorResponse = ErrorMapper.parse(response);
                    onFailure(call, new Throwable(errorResponse.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<GenresResponse> call, Throwable t) {
                view.showError(t);
                view.hideLoading();
            }
        });
    }

    private void clearViews() {
        view.hideEmptyView();
        view.hideErrorView();
    }
}
