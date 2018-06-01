package me.henriquecocito.themoviedb.detail;

import me.henriquecocito.themoviedb.base.BaseContract;
import me.henriquecocito.themoviedb.detail.data.model.DetailResponse;
import retrofit2.Call;
import retrofit2.Callback;

public interface DetailContract {
    interface View extends BaseContract.View {
        void showDetail(DetailResponse detail);
    }

    interface Presenter extends BaseContract.Presenter {
        void load(int id);
    }

    interface Domain {
        void loadDetail(int id, Callback<DetailResponse> callback);
    }

    interface Repository {
        Call<DetailResponse> getDetail(int id);
    }
}
