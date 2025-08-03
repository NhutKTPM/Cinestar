package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.MovieEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<ShowtimeEntity, Long> {
    List<ShowtimeEntity> findByMovie(MovieEntity movie);


    @Query("SELECT DISTINCT CAST(s.startingTime as LocalDate) as showDate FROM ShowtimeEntity s " +
            "WHERE s.movie = :movie AND s.startingTime > CURRENT_TIMESTAMP " +
            "ORDER BY showDate")
    List<LocalDate> findDistinctShowDatesByMovie(@Param("movie") MovieEntity movie);

    @Query("SELECT DISTINCT CAST(s.startingTime as LocalDate) as showDate FROM ShowtimeEntity s " +
            "WHERE s.room.cinema = :cinema AND s.startingTime > CURRENT_TIMESTAMP " +
            "ORDER BY showDate")
    List<LocalDate> findDistinctShowDatesByCinema(@Param("cinema") CinemaEntity cinema);

    @Query("SELECT s FROM ShowtimeEntity s WHERE s.movie = :movie AND DATE(s.startingTime) = :date")
    List<ShowtimeEntity> findByShowDate(@Param("movie") MovieEntity movie, @Param("date") LocalDate date);

    @Query("SELECT s FROM ShowtimeEntity s WHERE s.movie = :movie AND DATE(s.startingTime) = :date AND s.room.cinema = :cinema")
    List<ShowtimeEntity> findByMovieShowDateAndCinema(@Param("movie") MovieEntity movie, @Param("date") LocalDate date, @Param("cinema") CinemaEntity cinema);

    @Query("SELECT s FROM ShowtimeEntity s WHERE s.movie = :movie AND DATE(s.startingTime) = :date AND s.room.cinema.region = :region")
    List<ShowtimeEntity> findByShowDateAndRegion(@Param("movie") MovieEntity movie, @Param("date") LocalDate date,  @Param("region") RegionEntity region);


    @Query("SELECT s FROM ShowtimeEntity s WHERE DATE(s.startingTime) = :date AND s.room.cinema = :cinema")
    List<ShowtimeEntity> findByShowDateAndCinema(@Param("date") LocalDate date, @Param("cinema") CinemaEntity cinema);


}
