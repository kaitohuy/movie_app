package com.nhom8.movie_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nhom8.movie_app.R;
import com.nhom8.movie_app.adapters.MovieAdapter;
import com.nhom8.movie_app.dal.AppDatabase;
import com.nhom8.movie_app.entities.Movie;
import com.nhom8.movie_app.entities.ShowTime;
import com.nhom8.movie_app.entities.Theater;
import com.nhom8.movie_app.entities.User;
import com.nhom8.movie_app.utils.SharedPrefsHelper;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView tvGreeting;
    RecyclerView rvMovies;
    BottomNavigationView bottomNavigation;
    AppDatabase db;
    SharedPrefsHelper prefsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvGreeting = findViewById(R.id.tvGreeting);
        rvMovies = findViewById(R.id.rvMovies);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        db = AppDatabase.getInstance(this);
        prefsHelper = new SharedPrefsHelper(this);

        initDummyData();
        setupRecyclerView();
        setupBottomNav();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateGreeting();
        bottomNavigation.setSelectedItemId(R.id.nav_home);
    }

    private void updateGreeting() {
        if (prefsHelper.isLoggedIn()) {
            User currentUser = db.movieDao().getUserByUsername(prefsHelper.getCurrentUser());
            if (currentUser != null && currentUser.fullName != null) {
                tvGreeting.setText("Xin chào, " + currentUser.fullName + "!");
            } else {
                tvGreeting.setText("Xin chào, " + prefsHelper.getCurrentUser() + "!");
            }
        } else {
            tvGreeting.setText("Xin chào, Khách!");
        }
    }

    private void setupBottomNav() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                return true;

            } else if (itemId == R.id.nav_ticket) {
                // TODO: Chuyển sang màn hình Lịch sử đặt vé (TicketActivity)
                Toast.makeText(this, "Chức năng Vé sẽ làm ở phần sau!", Toast.LENGTH_SHORT).show();
                return false;

            } else if (itemId == R.id.nav_profile) {
                // TODO: Chuyển sang Login hoặc Profile
                Toast.makeText(this, "Chức năng Tôi sẽ làm ở phần sau!", Toast.LENGTH_SHORT).show();
                return false;
            } else if (itemId == R.id.nav_ticket) {
                if (prefsHelper.isLoggedIn()) {
                    startActivity(new Intent(this, TicketActivity.class));
                } else {
                    Toast.makeText(this, "Vui lòng đăng nhập để xem vé", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                }
                return false;
            }
            return false;
        });
    }

    private void setupRecyclerView() {
        // Dùng GridLayoutManager 2 cột thay vì LinearLayoutManager
        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));

        List<Movie> movieList = db.movieDao().getAllMovies();
        MovieAdapter adapter = new MovieAdapter(movieList);
        rvMovies.setAdapter(adapter);
    }

    private void initDummyData() {
        if (db.movieDao().getAllMovies().isEmpty()) {
            // 1. Dữ liệu User
            User u = new User();
            u.username = "admin";
            u.password = "123";
            u.fullName = "Huy Admin";
            u.phone = "0123456789";
            db.movieDao().insertUser(u);

            // 2. Dữ liệu Rạp chiếu
            Theater t1 = new Theater();
            t1.name = "CGV Vincom Center";
            t1.address = "Tầng 5 Vincom, Hà Nội";
            Theater t2 = new Theater();
            t2.name = "Lotte Cinema";
            t2.address = "Landmark 72, Hà Nội";
            db.movieDao().insertTheater(t1);
            db.movieDao().insertTheater(t2);

            // 3. Dữ liệu Phim (Nhớ thêm các file ảnh m1.png, m2.png... vào thư mục
            // res/drawable)
            Movie m1 = new Movie();
            m1.title = "Dune: Part Two";
            m1.duration = 166;
            m1.description = "Hành trình báo thù trên hành tinh cát.";
            m1.imageUrl = "m1";
            Movie m2 = new Movie();
            m2.title = "Kung Fu Panda 4";
            m2.duration = 94;
            m2.description = "Gấu Po trở lại với kẻ thù mới.";
            m2.imageUrl = "m2";
            Movie m3 = new Movie();
            m3.title = "Godzilla x Kong";
            m3.duration = 115;
            m3.description = "Cuộc chiến của các siêu quái vật.";
            m3.imageUrl = "m3";
            Movie m4 = new Movie();
            m4.title = "Exhuma: Quật Mộ Trùng Ma";
            m4.duration = 134;
            m4.description = "Kinh dị tâm linh Hàn Quốc.";
            m4.imageUrl = "m4";

            db.movieDao().insertMovie(m1);
            db.movieDao().insertMovie(m2);
            db.movieDao().insertMovie(m3);
            db.movieDao().insertMovie(m4);

            // 4. Dữ liệu Lịch chiếu (Khớp ID phim và ID rạp)
            // Phim 1 (Dune) chiếu ở Rạp 1
            ShowTime st1 = new ShowTime();
            st1.movieId = 1;
            st1.theaterId = 1;
            st1.time = "18:00 - 20/11";
            st1.price = 100000;
            ShowTime st2 = new ShowTime();
            st2.movieId = 1;
            st2.theaterId = 1;
            st2.time = "20:30 - 20/11";
            st2.price = 120000;
            // Phim 2 chiếu ở Rạp 2
            ShowTime st3 = new ShowTime();
            st3.movieId = 2;
            st3.theaterId = 2;
            st3.time = "09:00 - 21/11";
            st3.price = 80000;

            db.movieDao().insertShowTime(st1);
            db.movieDao().insertShowTime(st2);
            db.movieDao().insertShowTime(st3);
        }
    }
}