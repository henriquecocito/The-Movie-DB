package me.henriquecocito.themoviedb.movie.presentation;

import me.henriquecocito.themoviedb.base.data.model.ErrorMapper;
import me.henriquecocito.themoviedb.base.data.model.ErrorResponse;
import me.henriquecocito.themoviedb.movie.MovieContract;
import me.henriquecocito.themoviedb.movie.domain.MovieInteractor;
import me.henriquecocito.themoviedb.movie.data.model.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePresenter implements MovieContract.Presenter {

    private MovieContract.Domain interactor = new MovieInteractor();
    private MovieContract.View view;
    private int page = 1;

    public MoviePresenter(MovieContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
    }

    @Override
    public void load(int genreId) {
        clearViews();
        if(genreId > 0) {
            view.showLoading();
            interactor.loadMovies(genreId, page, new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if(response.isSuccessful()) {
                        if(response.body().getResults().size() > 0) {
                            page++;
                            view.showItems(response.body().getResults());
                        } else {
                            view.showEmptyView();
                        }
                        view.hideLoading();
                    } else {
                        ErrorResponse errorResponse = ErrorMapper.parse(response);
                        onFailure(call, new Throwable(errorResponse.getMessage()));
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    view.showError(t);
                    view.hideLoading();
                }
            });
        } else {
            view.showError(new Throwable("Genre not found"));
        }
    }

    @Override
    public void reset() {
        page = 1;
    }

    private void clearViews() {
        view.hideEmptyView();
        view.hideErrorView();
    }
}
