package me.henriquecocito.themoviedb.main;

import me.henriquecocito.themoviedb.base.BaseContract;

public interface MainContract {
    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter {
        void load();
    }

    interface Domain {

    }

    interface Repository {

    }
}
