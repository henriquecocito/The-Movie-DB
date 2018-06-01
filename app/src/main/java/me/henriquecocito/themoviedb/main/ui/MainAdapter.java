package me.henriquecocito.themoviedb.main.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.henriquecocito.themoviedb.R;
import me.henriquecocito.themoviedb.databinding.ItemMainBinding;
import me.henriquecocito.themoviedb.main.MainContract;
import me.henriquecocito.themoviedb.main.data.model.Genre;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private MainContract.View view;
    private Context context;
    private List<Genre> genres;

    public MainAdapter(Context context, MainContract.View view, List<Genre> genres) {
        this.view = view;
        this.genres = genres;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_main, parent, false);

        return new ViewHolder(binding, view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(genres.get(position));
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemMainBinding binding;
        private final MainContract.View view;

        public ViewHolder(ItemMainBinding binding, MainContract.View view) {
            super(binding.getRoot());
            this.binding = binding;
            this.view = view;
        }

        public void bind(final Genre genre) {
            binding.genre.setText(genre.getName());
            binding.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.openMovies(genre.getId(), genre.getName());
                }
            });
        }
    }
}
