package com.example.popularmovies_roomdb.data.remote;

import com.example.popularmovies_roomdb.data.Client;
import com.example.popularmovies_roomdb.data.Services;
import com.example.popularmovies_roomdb.data.model.Reviews;
import com.example.popularmovies_roomdb.data.model.Trailers;
import com.example.popularmovies_roomdb.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetReviewsRemote {

    public void getReviews(long movieId, BaseCallback errCallback, GetReviewsCallback callback) {

        Client.getInstance().create(Services.class).getReviews(movieId, Utils.API_KEY)
                .enqueue(new Callback<Reviews>() {
                    @Override
                    public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                        callback.onCallback(response.body());
                    }

                    @Override
                    public void onFailure(Call<Reviews> call, Throwable t) {
                        errCallback.onDataNotAvailable(null, null);
                    }
                });
    }

    public interface GetReviewsCallback {
        void onCallback(Reviews body);
    }
}
