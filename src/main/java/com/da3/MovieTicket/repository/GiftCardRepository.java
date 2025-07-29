package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.GiftCardEntity;
import com.da3.MovieTicket.entity.GiftCardImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCardRepository extends JpaRepository<GiftCardEntity, Long> {
    List<GiftCardEntity> findByRecipientUserId(Long recipientId);
    List<GiftCardEntity> findByPurchaserUserId(Long purchaserId);
}
