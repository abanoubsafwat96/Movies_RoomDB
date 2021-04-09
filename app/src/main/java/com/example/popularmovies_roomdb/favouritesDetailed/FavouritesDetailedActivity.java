package com.example.popularmovies_roomdb.favouritesDetailed;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bob.PopularMovies_ContentProvider.R;
import com.example.popularmovies_roomdb.base.BaseActivity;

public class FavouritesDetailedActivity extends BaseActivity<FavouritesDetailedViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_detailed);

        Bundle extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            FavouritesDetailedActivityFragment favouritesDetailedActivityFragment = new FavouritesDetailedActivityFragment();
            //pass the "estras" bundle that contain movie details
            favouritesDetailedActivityFragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, favouritesDetailedActivityFragment).commit();
        }
    }

    @Override
    public FavouritesDetailedViewModel getViewModel() {
        return new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(FavouritesDetailedViewModel.class);
    }
}
