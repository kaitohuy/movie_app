package com.nhom8.movie_app.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.nhom8.movie_app.R;
import com.nhom8.movie_app.dal.AppDatabase;
import com.nhom8.movie_app.entities.User;
import com.nhom8.movie_app.utils.SharedPrefsHelper;

public class ProfileActivity extends AppCompatActivity {

    ImageView btnBack;
    TextView tvFullName, tvUsername, tvPhone;
    MaterialButton btnLogout;
    SharedPrefsHelper prefsHelper;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnBack = findViewById(R.id.btnBack);
        tvFullName = findViewById(R.id.tvFullName);
        tvUsername = findViewById(R.id.tvUsername);
        tvPhone = findViewById(R.id.tvPhone);
        btnLogout = findViewById(R.id.btnLogout);

        prefsHelper = new SharedPrefsHelper(this);
        db = AppDatabase.getInstance(this);

        loadUserInfo();

        btnBack.setOnClickListener(v -> finish());

        btnLogout.setOnClickListener(v -> {
            prefsHelper.logout();
            finish();
        });
    }

    private void loadUserInfo() {
        String username = prefsHelper.getCurrentUser();
        User user = db.movieDao().getUserByUsername(username);

        if (user != null) {
            tvUsername.setText("@" + user.username);
            tvFullName.setText(user.fullName != null ? user.fullName : "Khách hàng");
            tvPhone.setText("SĐT: " + (user.phone != null ? user.phone : "Chưa cập nhật"));
        }
    }
}