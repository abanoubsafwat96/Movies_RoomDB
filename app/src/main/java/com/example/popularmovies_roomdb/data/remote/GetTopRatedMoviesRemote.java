package com.example.popularmovies_roomdb.data.remote;

import com.example.popularmovies_roomdb.data.ApiResponse;
import com.example.popularmovies_roomdb.data.Client;
import com.example.popularmovies_roomdb.data.ResponseWrapper;
import com.example.popularmovies_roomdb.data.Services;
import com.example.popularmovies_roomdb.data.model.Movie;
import com.example.popularmovies_roomdb.utils.Utils;

import java.util.List;


public class GetTopRatedMoviesRemote {

    public void getTopRatedMovies(int pageNum, BaseCallback errCallback, getTopRatedMoviesCallback callback) {

        Client.getInstance().create(Services.class).getTopRatedMovies(Utils.API_KEY, pageNum)
                .enqueue(new ResponseWrapper<ApiResponse<List<Movie>>>(errCallback) {
                    @Override
                    public void onSuccessCase(ApiResponse<List<Movie>> body) {
                        callback.onCallback(body);
                    }
                });
    }

    public interface getTopRatedMoviesCallback {
        void onCallback(ApiResponse<List<Movie>> bodey);
    }
}
