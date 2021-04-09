package com.example.popularmovies_roomdb.data.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.popularmovies_roomdb.data.model.Movie;

import java.util.List;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM Movie")
    List<Movie> getMoviesList();

    @Query("SELECT * FROM Movie")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM Movie WHERE id = :id")
    boolean isMovieFavourite(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Query("DELETE FROM Movie WHERE id = :id")
    void deleteMovie(long id);
}