package com.nhom8.movie_app.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.nhom8.movie_app.R;
import com.nhom8.movie_app.adapters.SeatAdapter;
import com.nhom8.movie_app.dal.AppDatabase;
import com.nhom8.movie_app.entities.ShowTimeView;
import com.nhom8.movie_app.entities.Ticket;
import com.nhom8.movie_app.utils.SharedPrefsHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookTicketActivity extends AppCompatActivity {

    ImageView btnBack;
    TextView tvTheaterInfo, tvTimeInfo, tvSelectedSeats, tvTotalPrice;
    RecyclerView rvSeats;
    MaterialButton btnConfirmBooking;

    AppDatabase db;
    SharedPrefsHelper prefsHelper;
    ShowTimeView currentShowTime;

    List<String> selectedSeats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);

        initViews();
        db = AppDatabase.getInstance(this);
        prefsHelper = new SharedPrefsHelper(this);

        int showTimeId = getIntent().getIntExtra("SHOWTIME_ID", -1);
        if (showTimeId != -1) {
            currentShowTime = db.movieDao().getShowTimeById(showTimeId);
            loadHeaderInfo();
            setupSeatGrid(showTimeId);
        }

        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvTheaterInfo = findViewById(R.id.tvTheaterInfo);
        tvTimeInfo = findViewById(R.id.tvTimeInfo);
        tvSelectedSeats = findViewById(R.id.tvSelectedSeats);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        rvSeats = findViewById(R.id.rvSeats);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);
    }

    private void loadHeaderInfo() {
        if (currentShowTime != null) {
            tvTheaterInfo.setText(currentShowTime.theaterName);
            tvTimeInfo.setText(currentShowTime.time);
        }
    }

    private void setupSeatGrid(int showTimeId) {
        // Tạo danh sách 25 ghế (A1->A5, B1->B5... E1->E5)
        List<String> allSeats = new ArrayList<>();
        String[] rows = { "A", "B", "C", "D", "E" };
        for (String row : rows) {
            for (int i = 1; i <= 5; i++) {
                allSeats.add(row + i);
            }
        }

        // Lấy danh sách ghế ĐÃ BỊ ĐẶT từ Database
        List<String> bookedSeats = db.movieDao().getBookedSeats(showTimeId);

        // Thiết lập RecyclerView dạng lưới 5 cột
        rvSeats.setLayoutManager(new GridLayoutManager(this, 5));

        SeatAdapter adapter = new SeatAdapter(allSeats, bookedSeats, seats -> {
            selectedSeats = seats;
            updateBottomBar();
        });
        rvSeats.setAdapter(adapter);
    }

    private void updateBottomBar() {
        if (selectedSeats.isEmpty()) {
            tvSelectedSeats.setText("Chưa chọn ghế");
            tvTotalPrice.setText("0 VNĐ");
            btnConfirmBooking.setEnabled(false);
        } else {
            // Hiển thị danh sách ghế (ví dụ: A1, A2)
            StringBuilder seatsStr = new StringBuilder("Ghế: ");
            for (int i = 0; i < selectedSeats.size(); i++) {
                seatsStr.append(selectedSeats.get(i));
                if (i < selectedSeats.size() - 1)
                    seatsStr.append(", ");
            }
            tvSelectedSeats.setText(seatsStr.toString());

            // Tính tiền
            double total = selectedSeats.size() * currentShowTime.price;
            tvTotalPrice.setText(String.format("%,.0f VNĐ", total));
            btnConfirmBooking.setEnabled(true);
        }
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnConfirmBooking.setOnClickListener(v -> {
            String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
            String username = prefsHelper.getCurrentUser();

            // Tạo từng vé cho mỗi ghế được chọn và lưu vào DB
            for (String seat : selectedSeats) {
                Ticket ticket = new Ticket();
                ticket.showTimeId = currentShowTime.showTimeId;
                ticket.username = username;
                ticket.seatName = seat;
                ticket.bookingDate = currentDate;
                db.movieDao().insertTicket(ticket);
            }

            // Hiện thông báo thành công
            new AlertDialog.Builder(this)
                    .setTitle("Đặt vé thành công!")
                    .setMessage("Bạn đã đặt " + selectedSeats.size() + " vé. \nTổng tiền: "
                            + tvTotalPrice.getText().toString())
                    .setCancelable(false)
                    .setPositiveButton("Tuyệt vời", (dialog, which) -> {
                        finish(); // Đóng trang Đặt vé
                    })
                    .show();
        });
    }
}