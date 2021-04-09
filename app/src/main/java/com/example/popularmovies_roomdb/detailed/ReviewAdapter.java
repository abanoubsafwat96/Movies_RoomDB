package com.example.popularmovies_roomdb.detailed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bob.PopularMovies_ContentProvider.R;
import com.example.popularmovies_roomdb.data.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob on 2016-09-17.
 */
public class ReviewAdapter extends BaseAdapter {

    public Context context;
    private List<Review> reviews_list;

    public ReviewAdapter(Context context, List<Review> list) {
        this.context = context;
        reviews_list = list;
    }

    @Override
    public int getCount() {
        return reviews_list.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.review_list_single_item, null);
        }
        Review review = (Review) getItem(position);

        TextView textView1 = (TextView) view.findViewById(R.id.movie_author);
        textView1.setText("A movie review by " + review.author);

        TextView textView2 = (TextView) view.findViewById(R.id.movie_review);
        textView2.setText(review.content);

        return view;
    }
}
