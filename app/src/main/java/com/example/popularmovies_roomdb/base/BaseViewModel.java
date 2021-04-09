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
import com.example.popularmovies_roomdb.data.remote.BaseCallback;
import com.example.popularmovies_roomdb.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;


public class BaseViewModel extends AndroidViewModel implements BaseCallback {

    public MutableLiveData<Boolean> paginationLoadingProgress = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingProgress = new MutableLiveData<>();
    public MutableLiveData<String> showMessage = new MutableLiveData<>();
    public MutableLiveData<Integer> showMessageRes = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    protected AppDatabase getDatabase() {
        return AppDatabase.getInstance(getApplication());
    }


    protected boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
            boolean conn = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!conn) {
                showMessageRes.setValue(R.string.offline);
            }
            return conn;
        }
        return false;

    }

    public void showHideLoadingProgress(boolean shouldClearList, boolean value, boolean enableLoadingProgress) {
        if (shouldClearList) {
            if (enableLoadingProgress)
                loadingProgress.setValue(value);
        }else
            paginationLoadingProgress.setValue(value);
    }

    @Override
    public void onDataNotAvailable(String errorMsg, String code) {
        loadingProgress.setValue(false);
        printErrorMsg(errorMsg);
    }

    protected void printErrorMsg(String errorMsg) {
        if (errorMsg == null || TextUtils.isEmpty(errorMsg)) {
            showMessageRes.setValue(R.string.serverError);
        } else {
            showMessage.setValue(errorMsg);
        }
    }


    public AppExecutors getExecutor(Runnable runnable) {
        AppExecutors appExecutors = new AppExecutors();
        appExecutors.diskIO().execute(runnable);
        return appExecutors;
    }
}
