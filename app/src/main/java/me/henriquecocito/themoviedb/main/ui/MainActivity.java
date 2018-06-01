package me.henriquecocito.themoviedb.main.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.henriquecocito.themoviedb.R;
import me.henriquecocito.themoviedb.databinding.ActivityMainBinding;
import me.henriquecocito.themoviedb.main.MainContract;
import me.henriquecocito.themoviedb.main.data.model.Genre;
import me.henriquecocito.themoviedb.main.presentation.MainPresenter;
import me.henriquecocito.themoviedb.movie.ui.MovieActivity;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ActivityMainBinding binding;
    private MainContract.Presenter presenter = new MainPresenter(this);
    private ArrayList genres = new ArrayList();
    private MainAdapter adapter = new MainAdapter(this, this, genres);

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
        if(genres.size() < 1)
            binding.container.addView(LayoutInflater.from(this).inflate(R.layout.view_error, binding.container, false));

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

    @Override
    public void showItems(List<Genre> genres) {
        this.genres.clear();
        this.genres.addAll(genres);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        binding.container.addView(LayoutInflater.from(this).inflate(R.layout.view_empty, binding.container, false));
    }

    @Override
    public void hideEmptyView() {
        View view = binding.container.findViewById(R.id.empty);
        if(view != null) {
            binding.container.removeView(view);
        }
    }

    @Override
    public void hideErrorView() {
        View view = binding.container.findViewById(R.id.error);
        if(view != null) {
            binding.container.removeView(view);
        }
    }

    @Override
    public void openMovies(int genreId, String genre) {
        startActivity(MovieActivity.getIntent(this, genreId, genre));
    }

    private void setupActionBar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_genres);
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
