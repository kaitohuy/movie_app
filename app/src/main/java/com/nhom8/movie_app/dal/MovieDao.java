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
}