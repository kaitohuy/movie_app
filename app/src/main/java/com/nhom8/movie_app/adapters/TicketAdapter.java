package com.nhom8.movie_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nhom8.movie_app.R;
import com.nhom8.movie_app.entities.TicketView;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<TicketView> ticketList;

    public TicketAdapter(List<TicketView> ticketList) {
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        TicketView ticket = ticketList.get(position);

        holder.tvMovieTitle.setText(ticket.movieTitle);
        holder.tvTheater.setText(ticket.theaterName);
        holder.tvTime.setText(ticket.time);
        holder.tvSeats.setText("Ghế: " + ticket.seats);
        holder.tvDate.setText("Ngày đặt: " + ticket.bookingDate);
        holder.tvTotal.setText(String.format("%,.0f đ", ticket.totalPrice));

        // Tải ảnh Poster
        if (ticket.imageUrl != null) {
            int imageResId = holder.itemView.getContext().getResources()
                    .getIdentifier(ticket.imageUrl, "drawable", holder.itemView.getContext().getPackageName());
            if (imageResId != 0) {
                holder.imgMovie.setImageResource(imageResId);
            }
        }
    }

    @Override
    public int getItemCount() {
        return ticketList != null ? ticketList.size() : 0;
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMovie;
        TextView tvMovieTitle, tvTheater, tvTime, tvSeats, tvDate, tvTotal;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.imgTicketMovie);
            tvMovieTitle = itemView.findViewById(R.id.tvTicketMovieTitle);
            tvTheater = itemView.findViewById(R.id.tvTicketTheater);
            tvTime = itemView.findViewById(R.id.tvTicketTime);
            tvSeats = itemView.findViewById(R.id.tvTicketSeats);
            tvDate = itemView.findViewById(R.id.tvTicketDate);
            tvTotal = itemView.findViewById(R.id.tvTicketTotal);
        }
    }
}