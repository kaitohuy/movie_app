package com.nhom8.movie_app.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nhom8.movie_app.R;
import com.nhom8.movie_app.activities.MovieDetailActivity;
import com.nhom8.movie_app.entities.Movie;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvMovieTitle.setText(movie.title);
        holder.tvMovieDuration.setText(movie.duration + " phút");

        // Hiển thị ảnh Poster
        if (movie.imageUrl != null) {
            int imageResId = holder.itemView.getContext().getResources()
                    .getIdentifier(movie.imageUrl, "drawable", holder.itemView.getContext().getPackageName());
            if (imageResId != 0) {
                holder.imgPoster.setImageResource(imageResId);
            } else {
                holder.imgPoster.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        }

        // Bấm vào chuyển sang trang Chi tiết Phim
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
            intent.putExtra("MOVIE_ID", movie.id);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvMovieTitle, tvMovieDuration;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvMovieDuration = itemView.findViewById(R.id.tvMovieDuration);
        }
    }
}