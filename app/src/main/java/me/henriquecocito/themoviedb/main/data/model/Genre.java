package me.henriquecocito.themoviedb.main.data.model;

import com.google.gson.annotations.SerializedName;

public class Genre {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
}
