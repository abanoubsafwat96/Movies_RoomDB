package com.example.popularmovies_roomdb.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bob.PopularMovies_ContentProvider.R;
import com.example.popularmovies_roomdb.Communicator;
import com.example.popularmovies_roomdb.MovieAdapter;
import com.example.popularmovies_roomdb.base.BaseFragment;
import com.example.popularmovies_roomdb.data.model.Movie;
import com.example.popularmovies_roomdb.main.MainViewModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends BaseFragment<SearchViewModel> {

    EditText searchEt;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MovieAdapter adapter;
    Communicator communicator;
    int myLastVisiblePos;
    SearchViewModel viewModel;
    private ArrayList<Movie> list;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        searchEt = root.findViewById(R.id.searchEt);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<Movie>();
        adapter = new MovieAdapter(list, communicator);
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
            searchEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    search(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            viewModel.getOnLoadMoreItems().observe(getViewLifecycleOwner(), onLoadMore -> {
                if (list.size() == 0 || onLoadMore == null || viewModel.isLastPage()) return;
                if (isConnected())
                    viewModel.searchMovies(false, getSeachQuery());
            });

            viewModel.movies_liveData.observe(requireActivity(), movies -> {
                if (movies == null) return;

                this.list.clear();
                this.list.addAll(movies);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            });
        }

        return root;
    }

    private void search(CharSequence s) {
        if (s.length() == 0) {
            list.clear();
            recyclerView.getAdapter().notifyDataSetChanged();
        } else {
            viewModel.searchMovies(true, getSeachQuery());
        }
    }

    private String getSeachQuery() {
        return searchEt.getText().toString();
    }

    public void setMovieCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public SearchViewModel getViewModel() {
        return new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(SearchViewModel.class);
    }
}
