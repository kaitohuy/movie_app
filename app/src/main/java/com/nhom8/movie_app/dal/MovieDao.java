package com.nhom8.movie_app.dal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nhom8.movie_app.entities.Movie;
import com.nhom8.movie_app.entities.ShowTime;
import com.nhom8.movie_app.entities.ShowTimeView;
import com.nhom8.movie_app.entities.Theater;
import com.nhom8.movie_app.entities.User;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTheater(Theater theater);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertShowTime(ShowTime showTime);

    @Query("SELECT * FROM Users WHERE username = :u AND password = :p")
    User login(String u, String p);

    @Query("SELECT * FROM Users WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM Movies")
    List<Movie> getAllMovies();

    // Các hàm Query cho việc Đặt vé chúng ta sẽ thêm sau ở Phần 3
    @Query("SELECT * FROM Movies WHERE id = :id")
    Movie getMovieById(int id);

    @Query("SELECT st.id as showTimeId, t.name as theaterName, st.time as time, st.price as price " +
            "FROM ShowTimes st JOIN Theaters t ON st.theaterId = t.id " +
            "WHERE st.movieId = :movieId")
    List<ShowTimeView> getShowTimesByMovieId(int movieId);

    // Lấy danh sách các ghế đã được đặt cho 1 suất chiếu cụ thể
    @Query("SELECT seatName FROM Tickets WHERE showTimeId = :showTimeId")
    List<String> getBookedSeats(int showTimeId);

    // Lưu vé mới vào Database
    @Insert
    void insertTicket(Ticket ticket);

    // Truy vấn phụ để lấy thông tin suất chiếu hiện tại
    @Query("SELECT st.id as showTimeId, t.name as theaterName, st.time as time, st.price as price " +
            "FROM ShowTimes st JOIN Theaters t ON st.theaterId = t.id " +
            "WHERE st.id = :showTimeId")
    ShowTimeView getShowTimeById(int showTimeId);

    // Lấy lịch sử đặt vé của User, gộp các ghế mua cùng lúc thành 1 hàng
    @Query("SELECT m.title as movieTitle, th.name as theaterName, st.time as time, " +
            "GROUP_CONCAT(t.seatName, ', ') as seats, SUM(st.price) as totalPrice, " +
            "t.bookingDate as bookingDate, m.imageUrl as imageUrl " +
            "FROM Tickets t " +
            "JOIN ShowTimes st ON t.showTimeId = st.id " +
            "JOIN Movies m ON st.movieId = m.id " +
            "JOIN Theaters th ON st.theaterId = th.id " +
            "WHERE t.username = :username " +
            "GROUP BY t.showTimeId, t.bookingDate " +
            "ORDER BY t.id DESC")
    List<TicketView> getMyTickets(String username);
}