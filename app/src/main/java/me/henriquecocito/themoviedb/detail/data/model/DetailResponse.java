package me.henriquecocito.themoviedb.detail.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DetailResponse {

    @SerializedName("original_title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private Date date;

    @SerializedName("vote_average")
    private float average;

    @SerializedName("backdrop_path")
    private String backdrop;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("homepage")
    private String homepage;

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Date getDate() {
        return date;
    }

    public float getAverage() {
        return average;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getPoster() {
        return poster;
    }

    public String getHomepage() {
        return homepage;
    }
}
