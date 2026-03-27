package com.nhom8.movie_app.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public int duration; // Thời lượng phim (phút)
    public String imageUrl; // Tên file ảnh (ví dụ: "m1", "m2")
}