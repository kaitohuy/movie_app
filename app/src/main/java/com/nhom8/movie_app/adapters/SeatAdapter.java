package com.nhom8.movie_app.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom8.movie_app.R;

import java.util.ArrayList;
import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {

    private List<String> allSeats;
    private List<String> bookedSeats;
    private List<String> selectedSeats = new ArrayList<>();
    private OnSeatSelectedListener listener;

    public interface OnSeatSelectedListener {
        void onSeatSelected(List<String> selectedSeats);
    }

    public SeatAdapter(List<String> allSeats, List<String> bookedSeats, OnSeatSelectedListener listener) {
        this.allSeats = allSeats;
        this.bookedSeats = bookedSeats;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        String seatName = allSeats.get(position);
        holder.tvSeat.setText(seatName);

        // Kiểm tra trạng thái ghế để tô màu
        if (bookedSeats.contains(seatName)) {
            holder.tvSeat.setBackgroundColor(Color.parseColor("#D32F2F")); // Đỏ: Đã đặt
            holder.tvSeat.setTextColor(Color.WHITE);
        } else if (selectedSeats.contains(seatName)) {
            holder.tvSeat.setBackgroundColor(Color.parseColor("#388E3C")); // Xanh lá: Đang chọn
            holder.tvSeat.setTextColor(Color.WHITE);
        } else {
            holder.tvSeat.setBackgroundColor(Color.parseColor("#E0E0E0")); // Xám: Trống
            holder.tvSeat.setTextColor(Color.BLACK);
        }

        // Xử lý sự kiện click chọn ghế
        holder.itemView.setOnClickListener(v -> {
            if (bookedSeats.contains(seatName)) {
                Toast.makeText(v.getContext(), "Ghế này đã có người đặt!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedSeats.contains(seatName)) {
                selectedSeats.remove(seatName); // Bỏ chọn
            } else {
                selectedSeats.add(seatName); // Chọn
            }

            notifyItemChanged(position); // Cập nhật lại màu ghế này
            listener.onSeatSelected(selectedSeats); // Báo về cho Activity tính tiền
        });
    }

    @Override
    public int getItemCount() {
        return allSeats != null ? allSeats.size() : 0;
    }

    public static class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView tvSeat;

        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSeat = itemView.findViewById(R.id.tvSeat);
        }
    }
}