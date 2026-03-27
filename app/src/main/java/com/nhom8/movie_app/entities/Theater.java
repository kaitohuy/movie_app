package com.nhom8.movie_app.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Theaters")
public class Theater {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String address;
}