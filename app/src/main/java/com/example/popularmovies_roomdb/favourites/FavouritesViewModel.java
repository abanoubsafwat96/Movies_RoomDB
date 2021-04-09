package com.example.popularmovies_roomdb.favourites;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovies_roomdb.base.BaseViewModel;
import com.example.popularmovies_roomdb.data.model.Movie;
import com.example.popularmovies_roomdb.data.model.Review;
import com.example.popularmovies_roomdb.data.model.Trailer;
import com.example.popularmovies_roomdb.data.remote.GetReviewsRemote;
import com.example.popularmovies_roomdb.data.remote.GetTrailersRemote;

import java.util.List;

public class FavouritesViewModel extends BaseViewModel {

    public MutableLiveData<List<Movie>> movies_liveData=new MutableLiveData<>();

    public FavouritesViewModel(@NonNull Application application) {
        super(application);
    }

    public void getAllFavourites() {
        getExecutor(() -> {
            List<Movie> moviesList = getDatabase().moviesDao().getMoviesList();
            movies_liveData.postValue(moviesList);
        });
    }
}
