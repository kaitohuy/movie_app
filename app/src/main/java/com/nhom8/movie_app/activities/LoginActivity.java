package com.nhom8.movie_app.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nhom8.movie_app.R;
import com.nhom8.movie_app.dal.AppDatabase;
import com.nhom8.movie_app.entities.User;
import com.nhom8.movie_app.utils.SharedPrefsHelper;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtUsername, edtPassword;
    Button btnLogin;
    AppDatabase db;
    SharedPrefsHelper prefsHelper;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefsHelper = new SharedPrefsHelper(this);
        db = AppDatabase.getInstance(this);

        btnBack = findViewById(R.id.btnBack);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnBack.setOnClickListener(v -> finish());
        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String u = edtUsername.getText().toString().trim();
        String p = edtPassword.getText().toString().trim();

        User user = db.movieDao().login(u, p);
        if (user != null) {
            prefsHelper.saveLoginSession(u);
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Đóng trang Login, quay lại màn hình trước đó
        } else {
            Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
        }
    }
}