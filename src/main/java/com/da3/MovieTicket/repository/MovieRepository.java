package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    List<MovieEntity> findTop8ByEnabledTrueAndShowDateLessThanEqualOrderByShowDateDesc(LocalDate currentDate);

    List<MovieEntity> findTop8ByEnabledTrueAndShowDateGreaterThanOrderByShowDateAsc(LocalDate currentDate);

}
