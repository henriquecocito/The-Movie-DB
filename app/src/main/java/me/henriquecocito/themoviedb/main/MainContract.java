package me.henriquecocito.themoviedb.main;

import java.util.List;

import me.henriquecocito.themoviedb.base.BaseContract;
import me.henriquecocito.themoviedb.main.data.model.Genre;
import me.henriquecocito.themoviedb.main.data.model.GenresResponse;
import retrofit2.Call;
import retrofit2.Callback;

public interface MainContract {
    interface View extends BaseContract.View {
        void showItems(List<Genre> genres);
        void showEmptyView();
        void hideEmptyView();
        void hideErrorView();
    }

    interface Presenter extends BaseContract.Presenter {
        void load();
    }

    interface Domain {
        void loadGenres(Callback<GenresResponse> callback);
    }

    interface Repository {
        Call<GenresResponse> getGenres();
    }
}
