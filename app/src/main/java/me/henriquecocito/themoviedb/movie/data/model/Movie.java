package me.henriquecocito.themoviedb.movie.data.model;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("id")
    private int id;

    @SerializedName("original_title")
    private String title;

    @SerializedName("poster_path")
    private String cover;

    @SerializedName("vote_average")
    private float rating;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public float getRating() {
        return rating;
    }

}
