package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.GiftCardImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCardImageRepository extends JpaRepository<GiftCardImageEntity, Long> {
}
