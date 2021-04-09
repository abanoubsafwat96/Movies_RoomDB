package com.example.popularmovies_roomdb.data;


public class ApiResponse<T> {

    public ApiResponse() {
    }

    private int page;
    private int total_pages;
    private int total_results;
    private T results;

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public T getResults() {
        return results;
    }
}
