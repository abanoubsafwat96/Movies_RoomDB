package com.example.popularmovies_roomdb.data;

import com.example.bob.PopularMovies_ContentProvider.BuildConfig;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public static final String MOVIE = "movie/";
    public static final String MOVIE_WITHOUT_SLASH = "movie";
    public static final String SEARCH = "search/";
    public static final String GENRE = "genre/";

    public static Retrofit getInstance() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel((BuildConfig.DEBUG) ? HttpLoggingInterceptor.Level.BODY: HttpLoggingInterceptor.Level.NONE);

        Interceptor headerInterceptor = chain -> {
            Request request = chain.request().newBuilder().build();
            return chain.proceed(request);
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
