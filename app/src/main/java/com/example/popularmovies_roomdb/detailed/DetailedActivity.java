package com.example.popularmovies_roomdb.detailed;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bob.PopularMovies_ContentProvider.R;
import com.example.popularmovies_roomdb.base.BaseActivity;
import com.example.popularmovies_roomdb.topRated.TopRatedViewModel;

public class DetailedActivity extends BaseActivity<DetailedViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Bundle extras=getIntent().getExtras();
        if(savedInstanceState==null){
            DetailedActivityFragment detailedActivityFragment = new DetailedActivityFragment();
            //pass the "extras" bundle that contain movie details
            detailedActivityFragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, detailedActivityFragment).commit();
        }
    }

    @Override
    public DetailedViewModel getViewModel() {
        return new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(DetailedViewModel.class);
    }
}
