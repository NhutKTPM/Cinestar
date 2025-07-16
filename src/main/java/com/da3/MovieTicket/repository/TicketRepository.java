package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
}
