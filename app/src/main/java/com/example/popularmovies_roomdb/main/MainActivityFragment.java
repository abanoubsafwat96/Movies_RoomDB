package com.example.popularmovies_roomdb.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
public class MainActivityFragment extends BaseFragment<MainViewModel> {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MovieAdapter adapter;
    Communicator communicator;
    int myLastVisiblePos;
    MainViewModel viewModel;
    private ArrayList<Movie> list;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<Movie>();
        adapter = new MovieAdapter(list,communicator);
        recyclerView.setAdapter(adapter);

        viewModel = getViewModel();

        myLastVisiblePos = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int currentFirstVisPos = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();

                if (currentFirstVisPos > myLastVisiblePos) {
                    //scroll down
                    if ((visibleItemCount + currentFirstVisPos) >= totalItemCount) {
                        //last element of recyclerview
                        viewModel.getOnLoadMoreItems().setValue(true);
                    }
                    Log.e("totalItemCount", totalItemCount + "");
                }
                if (currentFirstVisPos < myLastVisiblePos) {
                    //scroll up
                }
                myLastVisiblePos = currentFirstVisPos;
            }
        });

        if (isConnected()) {
            viewModel.getOnLoadMoreItems().observe(getViewLifecycleOwner(), onLoadMore -> {
                if (list.size() == 0 || onLoadMore == null || viewModel.isLastPage()) return;
                if (isConnected())
                    viewModel.getNowPlayingMovies(false);
            });

            viewModel.movies_liveData.observe(requireActivity(), movies -> {
                if (movies == null) return;

                this.list.addAll(movies);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            });
            viewModel.getNowPlayingMovies(true);
        }

        return root;
    }

    public void setMovieCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public MainViewModel getViewModel() {
        return new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MainViewModel.class);
    }
}
