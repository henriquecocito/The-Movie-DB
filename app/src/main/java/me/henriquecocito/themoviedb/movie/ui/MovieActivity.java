package me.henriquecocito.themoviedb.movie.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.henriquecocito.themoviedb.R;
import me.henriquecocito.themoviedb.databinding.ActivityMovieBinding;
import me.henriquecocito.themoviedb.detail.ui.DetailActivity;
import me.henriquecocito.themoviedb.movie.data.model.Movie;
import me.henriquecocito.themoviedb.movie.MovieContract;
import me.henriquecocito.themoviedb.movie.presentation.MoviePresenter;

public class MovieActivity extends AppCompatActivity implements MovieContract.View {

    private static final String GENRE_KEY = "genre";
    private static final String GENRE_NAME = "name";

    private ActivityMovieBinding binding;
    private MovieContract.Presenter presenter = new MoviePresenter(this);
    private ArrayList movies = new ArrayList();
    private MovieAdapter adapter = new MovieAdapter(this, this, movies);
    private int genreId;
    private String genre;

    public static Intent getIntent(Context context, int genreId, String genre) {
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra(GENRE_KEY, genreId);
        intent.putExtra(GENRE_NAME, genre);

        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        genreId = getIntent().getIntExtra(GENRE_KEY, 0);
        genre = getIntent().getStringExtra(GENRE_NAME);

        setupActionBar();
        setupSwipeRefresh();
        setupRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.load(genreId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void openMovie(Movie movie) {
        startActivity(DetailActivity.getIntent(this, movie.getId(), movie.getTitle()));
    }

    @Override
    public void showError(Throwable e) {
        if(movies.size() < 1)
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
    public void showItems(List<Movie> movies) {
        this.movies.addAll(movies);

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

    private void setupActionBar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(genre);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void setupSwipeRefresh() {
        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                movies.clear();
                presenter.reset();
                presenter.load(genreId);
            }
        });
    }

    private void setupRecyclerView() {
        binding.list.setAdapter(adapter);
        binding.list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();

                if(!binding.refresh.isRefreshing() && lm.getItemCount() <= lm.findLastVisibleItemPosition() + 3) {
                    presenter.load(genreId);
                }
            }
        });
    }
}
