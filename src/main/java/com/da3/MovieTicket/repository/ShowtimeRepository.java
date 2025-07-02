package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.MovieEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<ShowtimeEntity, Long> {
    List<ShowtimeEntity> findByMovie(MovieEntity movie);
}
