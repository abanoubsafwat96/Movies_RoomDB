package com.example.popularmovies_roomdb.detailed;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovies_roomdb.base.OfflineDBBaseViewModel;
import com.example.popularmovies_roomdb.data.model.Genre;
import com.example.popularmovies_roomdb.data.model.Review;
import com.example.popularmovies_roomdb.data.model.Trailer;
import com.example.popularmovies_roomdb.data.remote.GetGenresRemote;
import com.example.popularmovies_roomdb.data.remote.GetReviewsRemote;
import com.example.popularmovies_roomdb.data.remote.GetTrailersRemote;

import java.util.ArrayList;
import java.util.List;

public class DetailedViewModel extends OfflineDBBaseViewModel {

    public MutableLiveData<List<Trailer>> trailers_liveData = new MutableLiveData<>();
    public MutableLiveData<List<Review>> reviews_liveData = new MutableLiveData<>();
    public MutableLiveData<String> genres_liveData = new MutableLiveData<>();

    public DetailedViewModel(@NonNull Application application) {
        super(application);
    }

    public void getTrailers(long movieId) {
        loadingProgress.setValue(true);
        new GetTrailersRemote().getTrailers(movieId, this, body -> {
            loadingProgress.setValue(false);
            trailers_liveData.setValue(body.getYoutube());
        });
    }

    public void getReviews(long movieId) {
        loadingProgress.setValue(true);
        new GetReviewsRemote().getReviews(movieId, this, body -> {
            loadingProgress.setValue(false);
            reviews_liveData.setValue(body.getResults());
        });
    }

    public void getGenres(ArrayList<Integer> genreIds) {
        if (genreIds == null || genreIds.size() == 0) return;

        loadingProgress.setValue(true);
        new GetGenresRemote().getGenres(this, body -> {
            loadingProgress.setValue(false);

            String finalGenres = "";
            for (int i = 0; i < genreIds.size(); i++) {
                Integer genreId = genreIds.get(i);
                for (int j = 0; j < body.genres.size(); j++) {
                    Genre genreId_j = body.genres.get(j);
                    if (genreId == genreId_j.id) {
                        finalGenres += genreId_j.name + " ";
                        break;
                    }
                }
            }
            genres_liveData.setValue(finalGenres);
        });
    }
}
