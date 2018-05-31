package me.henriquecocito.themoviedb.base.data.model;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Response;

public class ErrorMapper {

    public static ErrorResponse parse(Response<?> response) {
        try {

            return new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
        } catch (IOException e) {

            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Unknown error");
            errorResponse.setStatus(500);

            return errorResponse;
        }
    }
}
