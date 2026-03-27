package com.nhom8.movie_app.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Users")
public class User {
    @PrimaryKey
    @NonNull
    public String username;
    public String password;
    public String fullName;
    public String phone;
}