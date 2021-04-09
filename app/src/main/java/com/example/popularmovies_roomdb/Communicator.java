package com.example.popularmovies_roomdb;

import com.example.popularmovies_roomdb.data.model.Movie;

public interface Communicator{
    void setMovie(Movie movie);
    void setMoviePosition(int position);
}

