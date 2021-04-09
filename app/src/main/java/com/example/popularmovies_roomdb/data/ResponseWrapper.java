package com.example.popularmovies_roomdb.data;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.popularmovies_roomdb.data.remote.BaseCallback;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ResponseWrapper<T extends ApiResponse> implements Callback<T> {
    private BaseCallback baseBaseCallback;

    public ResponseWrapper(BaseCallback baseBaseCallback) {
        this.baseBaseCallback = baseBaseCallback;
    }

    private void handleResponse(Response<T> response) {


        T body = response.body();

        if (response.isSuccessful() && body != null) {
            onSuccessCase(body);
        } else {
            handleError(response, baseBaseCallback);

        }
    }

    private void handleError(Response<T> response, BaseCallback callback) {
        String errorMessage = null, errorCode = null;
        try {
            String errorBody = response.errorBody().string();
            JSONObject error = new JSONObject(errorBody);

            if (errorBody.contains("message")) {
                errorMessage = error.getString("message");
                errorCode = error.getString("code");
                callback.onDataNotAvailable(errorMessage, errorCode);
            }

        } catch (Exception e) {
            callback.onDataNotAvailable(errorMessage, errorCode);
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        handleResponse(response);
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        Log.e("onFailure: ", t.getMessage());
        baseBaseCallback.onDataNotAvailable(null, null);
    }

    public abstract void onSuccessCase(T body);

}
