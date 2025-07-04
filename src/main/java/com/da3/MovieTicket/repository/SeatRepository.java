package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.RoomEntity;
import com.da3.MovieTicket.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findByRoomOrderByRowLabelAscSeatNumberAsc(RoomEntity room);
}
