package com.example.popularmovies_roomdb.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovies_roomdb.data.model.Movie;
import java.util.List;


public class PaginationBaseViewModel extends BaseViewModel {

    protected int pageNum = 1;
    protected boolean isLastPage;
    private MutableLiveData<Object> onLoadMoreItems = new MutableLiveData<>();
    public MutableLiveData<List<Movie>> movies_liveData=new MutableLiveData<>();

    public PaginationBaseViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Object> getOnLoadMoreItems() {
        return onLoadMoreItems;
    }

    public boolean isLastPage() {
        return isLastPage;
    }
}
