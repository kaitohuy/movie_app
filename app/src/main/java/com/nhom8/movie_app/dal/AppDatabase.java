package com.nhom8.movie_app.dal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nhom8.movie_app.entities.Movie;
import com.nhom8.movie_app.entities.ShowTime;
import com.nhom8.movie_app.entities.Theater;
import com.nhom8.movie_app.entities.Ticket;
import com.nhom8.movie_app.entities.User;

@Database(entities = {User.class, Movie.class, Theater.class, ShowTime.class, Ticket.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "MovieDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() // Tránh crash khi đổi cấu trúc bảng
                    .build();
        }
        return INSTANCE;
    }
}