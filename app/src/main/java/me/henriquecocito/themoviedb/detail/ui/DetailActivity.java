package me.henriquecocito.themoviedb.detail.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.DateFormat;
import java.util.Objects;

import me.henriquecocito.themoviedb.BuildConfig;
import me.henriquecocito.themoviedb.R;
import me.henriquecocito.themoviedb.databinding.ActivityDetailBinding;
import me.henriquecocito.themoviedb.detail.DetailContract;
import me.henriquecocito.themoviedb.detail.presentation.DetailPresenter;
import me.henriquecocito.themoviedb.detail.data.model.DetailResponse;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    private ActivityDetailBinding binding;
    private DetailContract.Presenter presenter = new DetailPresenter(this);

    private static final String MOVIE_KEY = "movie";
    private static final String MOVIE_NAME = "name";
    private int id;
    private String name;

    public static Intent getIntent(Context context, int id, String name) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(MOVIE_KEY, id);
        intent.putExtra(MOVIE_NAME, name);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        id = getIntent().getIntExtra(MOVIE_KEY, 0);
        name = getIntent().getStringExtra(MOVIE_NAME);

        setupActionBar();
        presenter.load(id);
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
    public void showDetail(DetailResponse detail) {
        showImage(binding.cover, detail.getBackdrop());
        binding.overview.setText(detail.getOverview());
        binding.rate.setText(String.valueOf(detail.getAverage()));

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        binding.date.setText(dateFormat.format(detail.getDate()));


        if(detail.getHomepage() != null) {
            String text = "<a href='%s'>%s</a>";
            binding.homepage.setClickable(true);
            binding.homepage.setMovementMethod(LinkMovementMethod.getInstance());
            binding.homepage.setText(Html.fromHtml(String.format(text, detail.getHomepage(), detail.getHomepage())));
        }
    }

    @Override
    public void showError(Throwable e) {
        binding.container.removeAllViews();
        binding.container.addView(LayoutInflater.from(this).inflate(R.layout.view_error, binding.container, false));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void setupActionBar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void showImage(ImageView view, String url) {
        Glide
                .with(view)
                .load(String.format(BuildConfig.IMAGE_URL, 500, url))
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_error)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(view);
    }
}
