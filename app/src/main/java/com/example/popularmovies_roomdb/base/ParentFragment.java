package com.example.popularmovies_roomdb.base;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.popularmovies_roomdb.data.database.AppDatabase;

public class ParentFragment extends Fragment {

    ParentActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ParentActivity) context;
    }

    public void showDialog() {
        activity.showDialog();
    }

    public void hideDialog() {
        activity.hideDialog();
    }

    public void showMessage(String message) {
        activity.showMessage(message);
    }

    public void showMessage(int message) {
        activity.showMessage(message);
    }

    public boolean isConnected() {
        return activity.isConnected();
    }

    public AppDatabase getDatabase() {
        return activity.getDatabase();
    }
}