package com.example.popularmovies_roomdb.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.bob.PopularMovies_ContentProvider.R;
import com.example.popularmovies_roomdb.data.database.AppDatabase;
import com.example.popularmovies_roomdb.utils.AppExecutors;


public abstract class ParentActivity extends AppCompatActivity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void removeFragments() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {
                if (fragment.getTag() == null || !fragment.getTag().contains("glide")) {

                    getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();

                }
            }
        }
    }

    public void addFragment(int containerId, Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment, tag)
                .commit();
    }

    public void addFragmentWithBackStack(int containerId, Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(containerId, fragment, tag)
                .commit();
    }

    public void replaceWithBackStack(int containerId, Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                //  .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .addToBackStack(null)
                .replace(containerId, fragment, tag)
                .commit();
    }

    public void replaceFragment(int containerId, Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment, tag)
                .commit();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(getApplicationContext());
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
            boolean conn = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!conn) {
                showMessage(R.string.offline);
            }
            return conn;

        }
        return false;

    }

    public void showDialog() {
        if (dialog != null && dialog.isShowing()) return;
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        } else {
            dialog = ProgressDialog.show(this, "", getString(R.string.loading));
            dialog.setCancelable(true);
        }
    }

    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void showMessage(int msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void hideSoftKeyboard() {
        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }//if

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    public AppExecutors getExecutor(Runnable runnable) {
        AppExecutors appExecutors = new AppExecutors();
        appExecutors.diskIO().execute(runnable);
        return appExecutors;
    }
}

