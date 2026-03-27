package com.nhom8.movie_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nhom8.movie_app.R;
import com.nhom8.movie_app.entities.ShowTimeView;
import java.util.List;

public class ShowTimeAdapter extends RecyclerView.Adapter<ShowTimeAdapter.ShowTimeViewHolder> {

    private List<ShowTimeView> showTimes;
    private OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(ShowTimeView showTime);
    }

    public ShowTimeAdapter(List<ShowTimeView> showTimes, OnBookClickListener listener) {
        this.showTimes = showTimes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShowTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent, false);
        return new ShowTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowTimeViewHolder holder, int position) {
        ShowTimeView st = showTimes.get(position);
        holder.tvTheaterName.setText(st.theaterName);
        holder.tvTime.setText(st.time);
        holder.tvPrice.setText(String.format("%,.0f VNĐ", st.price));

        holder.btnBook.setOnClickListener(v -> listener.onBookClick(st));
    }

    @Override
    public int getItemCount() {
        return showTimes != null ? showTimes.size() : 0;
    }

    public static class ShowTimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTheaterName, tvTime, tvPrice;
        Button btnBook;

        public ShowTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTheaterName = itemView.findViewById(R.id.tvTheaterName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}