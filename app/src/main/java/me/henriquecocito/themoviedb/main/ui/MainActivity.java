package me.henriquecocito.themoviedb.main.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import me.henriquecocito.themoviedb.R;
import me.henriquecocito.themoviedb.databinding.ActivityMainBinding;
import me.henriquecocito.themoviedb.main.MainContract;
import me.henriquecocito.themoviedb.main.presentation.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ActivityMainBinding binding;
    private MainContract.Presenter presenter = new MainPresenter(this);
    private ArrayList genres = new ArrayList(Arrays.asList("Teste"));
    private MainAdapter adapter = new MainAdapter(this, genres);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presenter.start();

        setupActionBar();
        setupSwipeRefresh();
        setupRecyclerView();
    }

    @Override
    public void showError(Throwable e) {
        Snackbar
                .make(binding.container, e.getLocalizedMessage(), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showLoading() {
        binding.refresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        binding.refresh.setRefreshing(false);
    }

    private void setupActionBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.title_movies);
    }

    private void setupSwipeRefresh() {
        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.load();
            }
        });
    }

    private void setupRecyclerView() {
        binding.list.setAdapter(adapter);
    }
}
