package com.example.popularmovies_roomdb.search;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.popularmovies_roomdb.base.PaginationBaseViewModel;
import com.example.popularmovies_roomdb.data.model.Movie;
import com.example.popularmovies_roomdb.data.remote.SearchMoviesRemote;

import java.util.List;

public class SearchViewModel extends PaginationBaseViewModel {

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public void searchMovies(boolean shouldClearList, String query) {
        new SearchMoviesRemote().searchMovie(shouldClearList ? 1 : pageNum + 1, query, this, body -> {
            pageNum = shouldClearList ? 1 : pageNum + 1;
            isLastPage = pageNum == body.getTotal_pages() - 1;
            List<Movie> value = movies_liveData.getValue();
            if (value == null || shouldClearList) {
                movies_liveData.setValue(body.getResults());
            } else {
                value.addAll(body.getResults());
                movies_liveData.setValue(value);
            }
        });
    }
}
