package me.henriquecocito.themoviedb.base;

public interface BaseContract {
    interface View {
        void finish();
        void showError(Throwable e);
        void showLoading();
        void hideLoading();
    }

    interface Presenter {
        void start();
    }
}
