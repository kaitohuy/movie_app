package com.nhom8.movie_app.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ShowTimes")
public class ShowTime {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int movieId;    // Khóa ngoại trỏ về Movies
    public int theaterId;  // Khóa ngoại trỏ về Theaters
    public String time;    // Ví dụ: "18:00 - 20/11/2026"
    public double price;   // Giá vé của suất chiếu này
}