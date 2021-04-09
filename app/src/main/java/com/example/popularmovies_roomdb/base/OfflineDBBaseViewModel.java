package com.example.popularmovies_roomdb.base;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bob.PopularMovies_ContentProvider.R;
import com.example.popularmovies_roomdb.data.database.AppDatabase;
import com.example.popularmovies_roomdb.data.model.Movie;
import com.example.popularmovies_roomdb.data.remote.BaseCallback;
import com.example.popularmovies_roomdb.utils.AppExecutors;


public class OfflineDBBaseViewModel extends BaseViewModel {

    public MutableLiveData<Boolean> isMovieFavourite_liveData = new MutableLiveData<>();

    public OfflineDBBaseViewModel(@NonNull Application application) {
        super(application);
    }

    public void isMovieFavourite(long id) {
        getExecutor(() -> {
            boolean isFavourite = getDatabase().moviesDao().isMovieFavourite(id);
            isMovieFavourite_liveData.postValue(isFavourite);
        });
    }

    public void markMovieAsFavourite(Movie movie) {
        getExecutor(() -> {
            getDatabase().moviesDao().insertMovie(movie);
        });
    }

    public void removeMovieFromFavourites(long id) {
        getExecutor(() -> {
            getDatabase().moviesDao().deleteMovie(id);
        });
    }
}
