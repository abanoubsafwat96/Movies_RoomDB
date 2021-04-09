package com.example.popularmovies_roomdb.detailed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bob.PopularMovies_ContentProvider.R;
import com.example.popularmovies_roomdb.data.model.Trailer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob on 2016-09-17.
 */
public class TrailerAdapter extends BaseAdapter {

    private Context context;
    private List<Trailer> trailers_list;

    public TrailerAdapter(Context context,List<Trailer> list){
        this.context=context;
        trailers_list=list;
    }

    @Override
    public int getCount() {
        return trailers_list.size();
    }

    @Override
    public Object getItem(int position) {
        return trailers_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            // if it's not recycled, initialize some attributes
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.trailer_list_single_item,null);
        }

        Trailer trailer = (Trailer) getItem(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.trailer_icon);
        imageView.setImageResource(R.drawable.youtubelogo);

        TextView textView = (TextView) view.findViewById(R.id.trailer_name);
        textView.setText(trailer.name);

        return view;
    }
}
