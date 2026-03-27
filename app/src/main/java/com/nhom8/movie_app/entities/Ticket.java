package com.nhom8.movie_app.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tickets")
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int showTimeId; // Khóa ngoại trỏ về ShowTimes
    public String username; // Khóa ngoại trỏ về Users
    public String seatName; // Ghế ngồi (Ví dụ: "A1", "B4")
    public String bookingDate; // Ngày đặt vé
}