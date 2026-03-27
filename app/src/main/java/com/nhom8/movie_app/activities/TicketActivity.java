package com.nhom8.movie_app.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom8.movie_app.R;
import com.nhom8.movie_app.adapters.TicketAdapter;
import com.nhom8.movie_app.dal.AppDatabase;
import com.nhom8.movie_app.entities.TicketView;
import com.nhom8.movie_app.utils.SharedPrefsHelper;

import java.util.List;

public class TicketActivity extends AppCompatActivity {

    ImageView btnBack;
    RecyclerView rvTickets;
    AppDatabase db;
    SharedPrefsHelper prefsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        btnBack = findViewById(R.id.btnBack);
        rvTickets = findViewById(R.id.rvTickets);

        db = AppDatabase.getInstance(this);
        prefsHelper = new SharedPrefsHelper(this);

        btnBack.setOnClickListener(v -> finish());

        rvTickets.setLayoutManager(new LinearLayoutManager(this));

        loadMyTickets();
    }

    private void loadMyTickets() {
        if (prefsHelper.isLoggedIn()) {
            String username = prefsHelper.getCurrentUser();
            List<TicketView> tickets = db.movieDao().getMyTickets(username);
            rvTickets.setAdapter(new TicketAdapter(tickets));
        }
    }
}