package me.henriquecocito.themoviedb.main.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import me.henriquecocito.themoviedb.R;
import me.henriquecocito.themoviedb.databinding.ItemMainBinding;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context context;
    private List<String> genres;

    public MainAdapter(Context context, List<String> genres) {
        this.genres = genres;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_main, parent, false);

        return new ViewHolder(binding);
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

        public ViewHolder(ItemMainBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String genre) {
            binding.genre.setText(genre);
        }
    }
}
