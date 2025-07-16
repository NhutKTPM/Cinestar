package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.BillEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Long> {
    List<BillEntity> findAllByUserOrderByCreatedAtDesc(UserEntity user);
}
