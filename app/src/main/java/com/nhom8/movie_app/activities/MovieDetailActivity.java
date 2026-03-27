package com.nhom8.movie_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom8.movie_app.R;
import com.nhom8.movie_app.adapters.ShowTimeAdapter;
import com.nhom8.movie_app.dal.AppDatabase;
import com.nhom8.movie_app.entities.Movie;
import com.nhom8.movie_app.entities.ShowTimeView;
import com.nhom8.movie_app.utils.SharedPrefsHelper;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView btnBack, imgMovieCover;
    TextView tvMovieTitle, tvMovieDuration, tvMovieDesc;
    RecyclerView rvShowTimes;

    AppDatabase db;
    SharedPrefsHelper prefsHelper;
    Movie currentMovie;

    // Biến cờ lưu ID lịch chiếu đang đợi để quay lại từ Login
    int pendingShowTimeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        btnBack = findViewById(R.id.btnBack);
        imgMovieCover = findViewById(R.id.imgMovieCover);
        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvMovieDuration = findViewById(R.id.tvMovieDuration);
        tvMovieDesc = findViewById(R.id.tvMovieDesc);
        rvShowTimes = findViewById(R.id.rvShowTimes);

        db = AppDatabase.getInstance(this);
        prefsHelper = new SharedPrefsHelper(this);

        int movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        if (movieId != -1) {
            currentMovie = db.movieDao().getMovieById(movieId);
            loadMovieData();
            loadShowTimes(movieId);
        }

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadMovieData() {
        if (currentMovie != null) {
            tvMovieTitle.setText(currentMovie.title);
            tvMovieDuration.setText("Thời lượng: " + currentMovie.duration + " phút");
            tvMovieDesc.setText(currentMovie.description);

            if (currentMovie.imageUrl != null) {
                int resId = getResources().getIdentifier(currentMovie.imageUrl, "drawable", getPackageName());
                if (resId != 0) imgMovieCover.setImageResource(resId);
            }
        }
    }

    private void loadShowTimes(int movieId) {
        rvShowTimes.setLayoutManager(new LinearLayoutManager(this));
        List<ShowTimeView> showTimeList = db.movieDao().getShowTimesByMovieId(movieId);

        ShowTimeAdapter adapter = new ShowTimeAdapter(showTimeList, showTime -> {
            // Khi bấm ĐẶT VÉ
            if (!prefsHelper.isLoggedIn()) {
                Toast.makeText(this, "Vui lòng đăng nhập để đặt vé!", Toast.LENGTH_SHORT).show();
                pendingShowTimeId = showTime.showTimeId; // Lưu lại để tí đăng nhập xong xử lý tiếp
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                goToBookTicket(showTime.showTimeId);
            }
        });
        rvShowTimes.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Tự động nhảy sang Màn Chọn Ghế nếu vừa đăng nhập xong
        if (pendingShowTimeId != -1 && prefsHelper.isLoggedIn()) {
            int showTimeToBook = pendingShowTimeId;
            pendingShowTimeId = -1; // Reset cờ
            goToBookTicket(showTimeToBook);
        }
    }

    private void goToBookTicket(int showTimeId) {
        // TODO: Phần 5 sẽ làm màn hình chọn ghế
        Toast.makeText(this, "Chuyển sang màn hình chọn ghế (Phần sau)", Toast.LENGTH_SHORT).show();
        /*
        Intent intent = new Intent(this, BookTicketActivity.class);
        intent.putExtra("SHOWTIME_ID", showTimeId);
        startActivity(intent);
        */
    }
}