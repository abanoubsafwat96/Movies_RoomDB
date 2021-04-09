package com.example.popularmovies_roomdb.topRated;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.popularmovies_roomdb.base.PaginationBaseViewModel;
import com.example.popularmovies_roomdb.data.remote.GetTopRatedMoviesRemote;

public class TopRatedViewModel extends PaginationBaseViewModel {

    public TopRatedViewModel(@NonNull Application application) {
        super(application);
    }

    public void getTopRatedMovies(boolean shouldClearList) {

        loadingProgress.setValue(true);
        new GetTopRatedMoviesRemote().getTopRatedMovies(shouldClearList ? 1 : pageNum + 1, this, body -> {
            loadingProgress.setValue(false);
            pageNum = shouldClearList ? 1 : pageNum + 1;
            isLastPage = pageNum == body.getTotal_pages() - 1;
            movies_liveData.setValue(body.getResults());
        });
    }
}
