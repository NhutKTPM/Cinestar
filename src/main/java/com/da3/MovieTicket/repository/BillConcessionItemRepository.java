package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.BillConcessionItemEntity;
import com.da3.MovieTicket.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillConcessionItemRepository extends JpaRepository<BillConcessionItemEntity, Long> {
}
