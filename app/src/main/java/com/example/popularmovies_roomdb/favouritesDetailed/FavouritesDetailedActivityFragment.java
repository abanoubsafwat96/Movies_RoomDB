package com.example.popularmovies_roomdb.favouritesDetailed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.bob.PopularMovies_ContentProvider.R;
import com.example.popularmovies_roomdb.base.BaseFragment;
import com.example.popularmovies_roomdb.data.model.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavouritesDetailedActivityFragment extends BaseFragment<FavouritesDetailedViewModel> {

    Movie movie;
    ToggleButton toggleButton;
    private FavouritesDetailedViewModel viewModel;
    private Boolean isMovieFavourite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourites_detailed, container, false);

        movie = getArguments().getParcelable("movie");
        movie.genre_ids = getArguments().getIntegerArrayList("genres");

        viewModel = getViewModel();

        //poster
        String posterUrl = "http://image.tmdb.org/t/p/w342/" + movie.poster_path;
        Glide.with(getContext())
                .load(posterUrl)
                .into((ImageView) root.findViewById(R.id.movie_poster));

        //details
        ((TextView) root.findViewById(R.id.movie_title)).setText(movie.title);
        ((TextView) root.findViewById(R.id.vote_average)).setText("Vote average: " + movie.vote_average);
        ((TextView) root.findViewById(R.id.movie_release_date)).setText("Release date: " + movie.release_date);
        ((TextView) root.findViewById(R.id.movie_overview)).setText(movie.overview);

        //Remove from Favourites button

        toggleButton = (ToggleButton) root.findViewById(R.id.myToggleButton);

        viewModel.isMovieFavourite_liveData.observe(requireActivity(), isMovieFavourite -> {
            if (isMovieFavourite == null) return;

            this.isMovieFavourite = isMovieFavourite;

            if (isMovieFavourite)
                toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.starfav));
            else
                toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.starnonfav));
        });
        viewModel.isMovieFavourite(movie.id);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isMovieFavourite) {
                    viewModel.removeMovieFromFavourites(movie.id);
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.starnonfav));
                    Toast.makeText(getContext(), R.string.REMOVED_FROM_FAVOURITES, Toast.LENGTH_LONG).show();
                    isMovieFavourite = false;

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", "refresh");
                    getActivity().setResult(Activity.RESULT_OK, returnIntent);
                } else {
                    viewModel.markMovieAsFavourite(movie);
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.starfav));
                    Toast.makeText(getContext(), R.string.MARKED_AS_FAVOURITES, Toast.LENGTH_LONG).show();
                    isMovieFavourite = true;

                    // we must set another result to prevert removing movie from FavMain activity
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", "don't refresh");
                    getActivity().setResult(Activity.RESULT_OK, returnIntent);
                }
            }
        });

        //backdrop photo
        String posterUrl2 = "http://image.tmdb.org/t/p/w185/" + movie.backdrop_path;
        Glide.with(getContext())
                .load(posterUrl2)
                .into((ImageView) root.findViewById(R.id.movie_backdrop_path));

        return root;
    }

    @Override
    public FavouritesDetailedViewModel getViewModel() {
        return new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(FavouritesDetailedViewModel.class);
    }
}
