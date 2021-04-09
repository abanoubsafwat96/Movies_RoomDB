package com.example.popularmovies_roomdb.data;


import com.example.popularmovies_roomdb.data.model.Genre;
import com.example.popularmovies_roomdb.data.model.Genres;
import com.example.popularmovies_roomdb.data.model.Movie;
import com.example.popularmovies_roomdb.data.model.Reviews;
import com.example.popularmovies_roomdb.data.model.Trailer;
import com.example.popularmovies_roomdb.data.model.Trailers;

import java.lang.ref.Reference;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Services {

    @GET(Client.MOVIE + "now_playing")
    Call<ApiResponse<List<Movie>>> getNowPlayingMovies(@Query("api_key") String api_key,
                                                       @Query("page") int page);

    @GET(Client.MOVIE + "top_rated")
    Call<ApiResponse<List<Movie>>> getTopRatedMovies(@Query("api_key") String api_key,
                                                     @Query("page") int page);

    @GET(Client.MOVIE + "{movieId}/trailers")
    Call<Trailers> getTrailers(@Path("movieId") long movieId,
                               @Query("api_key") String api_key);

    @GET(Client.MOVIE + "{movieId}/reviews")
    Call<Reviews> getReviews(@Path("movieId") long movieId,
                             @Query("api_key") String api_key);

    @GET(Client.SEARCH + Client.MOVIE_WITHOUT_SLASH)
    Call<ApiResponse<List<Movie>>> searchMovie(@Query("api_key") String api_key,
                                               @Query("page") int page,
                                               @Query("query") String query);

    @GET(Client.GENRE + Client.MOVIE + "list")
    Call<Genres> getGenres(@Query("api_key") String api_key);
}
