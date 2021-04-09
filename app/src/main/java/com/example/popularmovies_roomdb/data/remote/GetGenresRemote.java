package com.example.popularmovies_roomdb.data.remote;

import com.example.popularmovies_roomdb.data.ApiResponse;
import com.example.popularmovies_roomdb.data.Client;
import com.example.popularmovies_roomdb.data.ResponseWrapper;
import com.example.popularmovies_roomdb.data.Services;
import com.example.popularmovies_roomdb.data.model.Genres;
import com.example.popularmovies_roomdb.data.model.Movie;
import com.example.popularmovies_roomdb.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetGenresRemote {

    public void getGenres(BaseCallback errCallback, OnCallback callback) {

        Client.getInstance().create(Services.class).getGenres(Utils.API_KEY)
                .enqueue(new Callback<Genres>() {
                    @Override
                    public void onResponse(Call<Genres> call, Response<Genres> response) {
                        callback.onCallback(response.body());
                    }

                    @Override
                    public void onFailure(Call<Genres> call, Throwable t) {
                        errCallback.onDataNotAvailable(null, null);
                    }
                });
    }

    public interface OnCallback {
        void onCallback(Genres body);
    }
}
