package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.GiftCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCardRepository extends JpaRepository<GiftCardEntity, Long> {
    List<GiftCardEntity> findByRecipientUserId(Long recipientId);
    List<GiftCardEntity> findByRecipientUserIdAndCurrentBalanceGreaterThan(Long recipientId, Long zero);
    List<GiftCardEntity> findByPurchaserUserId(Long purchaserId);
}
