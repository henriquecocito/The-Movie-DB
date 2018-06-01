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

    @SerializedName("homepage")
    private String homepage;

    public DetailResponse(String title, String overview, Date date, float average, String backdrop, String homepage) {
        this.title = title;
        this.overview = overview;
        this.date = date;
        this.average = average;
        this.backdrop = backdrop;
        this.homepage = homepage;
    }

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

    public String getHomepage() {
        return homepage;
    }
}
