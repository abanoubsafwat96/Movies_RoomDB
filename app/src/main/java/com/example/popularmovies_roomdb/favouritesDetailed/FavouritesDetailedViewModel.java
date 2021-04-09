package com.example.popularmovies_roomdb.favouritesDetailed;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovies_roomdb.base.BaseViewModel;
import com.example.popularmovies_roomdb.base.OfflineDBBaseViewModel;
import com.example.popularmovies_roomdb.data.model.Movie;
import com.example.popularmovies_roomdb.data.model.Review;
import com.example.popularmovies_roomdb.data.model.Trailer;
import com.example.popularmovies_roomdb.data.remote.GetReviewsRemote;
import com.example.popularmovies_roomdb.data.remote.GetTrailersRemote;

import java.util.List;

public class FavouritesDetailedViewModel extends OfflineDBBaseViewModel {

    public FavouritesDetailedViewModel(@NonNull Application application) {
        super(application);
    }

}
