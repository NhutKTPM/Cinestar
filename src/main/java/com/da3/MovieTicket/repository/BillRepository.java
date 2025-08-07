package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Long> {
    List<BillEntity> findAllByUserOrderByCreatedAtDesc(UserEntity user);

    List<BillEntity> findAllByShowtime(ShowtimeEntity showtime);

    List<BillEntity> findByCreatedAtGreaterThanEqualAndEnabledOrderByCreatedAtAsc(LocalDateTime date, boolean enabled);

    List<BillEntity> findAllByShowtimeMovieOrderByCreatedAtDesc(MovieEntity movie);
}
