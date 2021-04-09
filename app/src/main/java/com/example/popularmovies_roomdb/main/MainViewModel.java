package com.example.popularmovies_roomdb.main;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.popularmovies_roomdb.base.PaginationBaseViewModel;
import com.example.popularmovies_roomdb.data.remote.GetNowPlayingMoviesRemote;

public class MainViewModel extends PaginationBaseViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void getNowPlayingMovies(boolean shouldClearList) {

        loadingProgress.setValue(true);
        new GetNowPlayingMoviesRemote().getNowPlayingMovies(shouldClearList ? 1 : pageNum + 1, this, body -> {
            loadingProgress.setValue(false);
            pageNum = shouldClearList ? 1 : pageNum + 1;
            isLastPage = pageNum == body.getTotal_pages() - 1;
            movies_liveData.setValue(body.getResults());
        });
    }
}
