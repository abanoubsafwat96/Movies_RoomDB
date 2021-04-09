package com.example.popularmovies_roomdb.favourites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bob.PopularMovies_ContentProvider.R;
import com.example.popularmovies_roomdb.Communicator;
import com.example.popularmovies_roomdb.base.BaseFragment;
import com.example.popularmovies_roomdb.data.model.Movie;
import com.example.popularmovies_roomdb.MovieAdapter;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavouritesMainActivityFragment extends BaseFragment<FavouritesViewModel> {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MovieAdapter adapter;
    Communicator communicator;
    private FavouritesViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourites, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Movie> favourites_list = new ArrayList<Movie>();
        adapter = new MovieAdapter(favourites_list, communicator);
        recyclerView.setAdapter(adapter);

        viewModel = getViewModel();

        viewModel.movies_liveData.observe(requireActivity(), movies -> {
            if (movies == null) return;

            favourites_list.addAll(movies);
            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        });
        viewModel.getAllFavourites();

        return root;
    }

    public void setMovieCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public FavouritesViewModel getViewModel() {
        return new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(FavouritesViewModel.class);
    }
}
