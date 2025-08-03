package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.BillEntity;
import com.da3.MovieTicket.entity.GiftCardEntity;
import com.da3.MovieTicket.repository.GiftCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class GiftCardService {
    private final GiftCardRepository giftCardRepository;

    @Autowired
    public GiftCardService(GiftCardRepository giftCardRepository){
        this.giftCardRepository = giftCardRepository;
    }

    public List<GiftCardEntity> getAllGiftCards (){
        return giftCardRepository.findAll();
    }

    public void createGiftCard(GiftCardEntity giftCard){
        giftCard.setGiftCardCode(generateUniqueCode());
        giftCard.setExpirationDate(LocalDateTime.now().plusYears(1));
        giftCardRepository.save(giftCard);
    }

    public GiftCardEntity getGiftCardById(Long id) {
        return giftCardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid giftCard Id: " + id));
    }

    private String generateUniqueCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<GiftCardEntity> getAllGiftCardsOfRecipient(Long recipientId){
        return giftCardRepository.findByRecipientUserId(recipientId);
    }

    public List<GiftCardEntity> getAllGiftCardsOfRecipientCurrentBalanceGreateThanZero(Long recipientId){
        return giftCardRepository.findByRecipientUserIdAndCurrentBalanceGreaterThan(recipientId, 0L);
    }

    public List<GiftCardEntity> getAllGiftCardsOfPurchaser(Long purchaserId){
        return giftCardRepository.findByPurchaserUserId(purchaserId);
    }

    public void useGiftCard(GiftCardEntity giftCard, Long amountUsing){
        giftCard.setCurrentBalance(giftCard.getCurrentBalance() - amountUsing);
        giftCardRepository.save(giftCard);
    }



    public Long getTotalAllTimeGiftCardsSold() {
        List<GiftCardEntity> allGiftCards = getAllGiftCards();
        return allGiftCards.stream()
                .filter(GiftCardEntity::isEnabled)  // Only count enabled bills
                .mapToLong(GiftCardEntity::getInitialBalance)
                .sum();
    }

}
