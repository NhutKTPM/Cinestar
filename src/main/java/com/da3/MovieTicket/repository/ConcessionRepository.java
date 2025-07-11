package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.ConcessionEntity;
import com.da3.MovieTicket.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConcessionRepository extends JpaRepository<ConcessionEntity, Long> {

}
