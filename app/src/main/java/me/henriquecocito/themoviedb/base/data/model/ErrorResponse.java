package me.henriquecocito.themoviedb.base.data.model;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("status_code")
    private int status;

    @SerializedName("status_message")
    private String message;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
