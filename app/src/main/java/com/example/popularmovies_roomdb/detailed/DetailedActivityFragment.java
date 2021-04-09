package com.example.popularmovies_roomdb.detailed;

import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.bob.PopularMovies_ContentProvider.R;
import com.example.popularmovies_roomdb.base.BaseFragment;
import com.example.popularmovies_roomdb.data.model.Movie;
import com.example.popularmovies_roomdb.data.model.Trailer;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailedActivityFragment extends BaseFragment<DetailedViewModel> {

    boolean isMovieFavourite;
    Movie movie;
    ToggleButton toggleButton;
    ListView list1, list2;
    private DetailedViewModel viewModel;
    private TextView movieGenres;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detailed, container, false);

        movie = getArguments().getParcelable("movie");
        movie.genre_ids = getArguments().getIntegerArrayList("genres");
        viewModel = getViewModel();

        //poster
        String posterUrl = "http://image.tmdb.org/t/p/w342/" + movie.poster_path;
        Glide.with(getActivity())
                .load(posterUrl)
                .into((ImageView) root.findViewById(R.id.movie_poster));

        //details
        ((TextView) root.findViewById(R.id.movie_title)).setText(movie.title);
        ((TextView) root.findViewById(R.id.vote_average)).setText("Votes: " + movie.vote_average + " (" + movie.vote_count + ")");
        ((TextView) root.findViewById(R.id.movie_release_date)).setText("Release date: " + movie.release_date);
        ((TextView) root.findViewById(R.id.movie_overview)).setText(movie.overview);
        movieGenres = (TextView) root.findViewById(R.id.movie_genres);
        //Favourites button
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
                } else {
                    viewModel.markMovieAsFavourite(movie);
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.starfav));
                    Toast.makeText(getContext(), R.string.MARKED_AS_FAVOURITES, Toast.LENGTH_LONG).show();
                    isMovieFavourite = true;
                }
            }
        });

        //trailers and Reviews
        list1 = (ListView) root.findViewById(R.id.TrailerslistView);
        list2 = (ListView) root.findViewById(R.id.ReviewslistView);

        if (isConnected()) {

            getGenres();
            getTrailers();
            getReviews();
        }

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                TrailerAdapter trailerAdapter = (TrailerAdapter) adapterView.getAdapter();
                Trailer trailer = (Trailer) trailerAdapter.getItem(position);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailer.source));
                Log.e("trailtask onPostExe:", "https://www.youtube.com/watch?v=" + trailer.source);
                startActivity(intent);
            }
        });

        //backdrop photo
        String posterUrl2 = "http://image.tmdb.org/t/p/w185/" + movie.backdrop_path;
        Glide.with(getContext())
                .load(posterUrl2)
                .into((ImageView) root.findViewById(R.id.movie_backdrop_path));

        return root;
    }

    private void getReviews() {
        viewModel.reviews_liveData.observe(requireActivity(), reviews -> {
            if (reviews == null) return;

            ReviewAdapter adapter2 = new ReviewAdapter(getContext(), reviews);
            list2.setAdapter(adapter2);
            getTotalHeightofListView(list2);
        });
        viewModel.getReviews(movie.id);
    }

    private void getTrailers() {
        viewModel.trailers_liveData.observe(requireActivity(), trailers -> {
            if (trailers == null) return;

            TrailerAdapter adapter1 = new TrailerAdapter(getContext(), trailers);
            list1.setAdapter(adapter1);
            getTotalHeightofListView(list1);
        });
        viewModel.getTrailers(movie.id);
    }

    private void getGenres() {

        viewModel.genres_liveData.observe(requireActivity(), genres -> {
            if (genres == null) return;

            movieGenres.setText(genres);
        });
        viewModel.getGenres(movie.genre_ids);
    }

    //method to calculate the height of listview inside scrollview to show full list without need scrolling
    public static void getTotalHeightofListView(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if (listItem != null) {
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public DetailedViewModel getViewModel() {
        return new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(DetailedViewModel.class);
    }
}
