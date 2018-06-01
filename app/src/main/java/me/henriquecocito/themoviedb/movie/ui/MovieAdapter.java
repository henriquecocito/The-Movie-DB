package me.henriquecocito.themoviedb.movie.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import me.henriquecocito.themoviedb.BuildConfig;
import me.henriquecocito.themoviedb.R;
import me.henriquecocito.themoviedb.databinding.ItemMovieBinding;
import me.henriquecocito.themoviedb.movie.data.model.Movie;
import me.henriquecocito.themoviedb.movie.MovieContract;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movies;
    private Context context;
    private MovieContract.View view;

    public MovieAdapter(Context context, MovieContract.View view, List<Movie> movies) {
        this.context = context;
        this.view = view;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemMovieBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_movie, parent, false);

        return new ViewHolder(binding, view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemMovieBinding binding;
        private final MovieContract.View view;

        private ViewHolder(ItemMovieBinding itemView, MovieContract.View view) {
            super(itemView.getRoot());
            this.binding = itemView;
            this.view = view;
        }

        public void bind(final Movie movie) {
            binding.genre.setText(movie.getTitle());
            binding.rating.setRating(movie.getRating());
            binding.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.openMovie(movie);
                }
            });

            Glide
                    .with(binding.getRoot())
                    .load(String.format(BuildConfig.IMAGE_URL, 200, movie.getCover()))
                    .transition(withCrossFade())
                    .apply(new RequestOptions()
                            .error(R.drawable.ic_error)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    )
                    .into(binding.image);
        }
    }
}
