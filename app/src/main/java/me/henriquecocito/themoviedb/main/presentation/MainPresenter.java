package me.henriquecocito.themoviedb.main.presentation;

import me.henriquecocito.themoviedb.main.MainContract;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        load();
    }

    @Override
    public void load() {
        view.showLoading();
    }
}
