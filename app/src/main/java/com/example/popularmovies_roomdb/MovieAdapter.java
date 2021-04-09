package com.example.popularmovies_roomdb;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bob.PopularMovies_ContentProvider.R;
import com.example.popularmovies_roomdb.data.model.Movie;

import java.util.ArrayList;

/**
 * Created by Bob on 2016-08-25.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Communicator communicator;
    ArrayList<Movie> movies_list;

    public MovieAdapter(ArrayList<Movie> movies_list, Communicator communicator) {
        this.movies_list = movies_list;
        this.communicator=communicator;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.movie_single_item, null);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movie movie = movies_list.get(position);
        String posterUrl = "http://image.tmdb.org/t/p/w342" + movie.poster_path;

        Glide.with(holder.itemView.getContext())
                .load(posterUrl)
                .into(holder.imageView);
        holder.name.setText(movie.title+" ("+movie.vote_average +")");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return movies_list.size();
    }

    public void deleteItem(int position) {
        movies_list.remove(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            name = (TextView) itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie movie = movies_list.get(position);

            if (movie == null) {
                return;
            }
            communicator.setMoviePosition(position);
            communicator.setMovie(movie);
        }
    }
}
