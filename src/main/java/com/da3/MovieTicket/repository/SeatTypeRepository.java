package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.entity.SeatTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatTypeEntity, Long> {
}
