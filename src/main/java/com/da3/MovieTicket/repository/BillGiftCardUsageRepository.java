package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.BillConcessionItemEntity;
import com.da3.MovieTicket.entity.BillGiftCardUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillGiftCardUsageRepository extends JpaRepository<BillGiftCardUsageEntity, Long> {
}
