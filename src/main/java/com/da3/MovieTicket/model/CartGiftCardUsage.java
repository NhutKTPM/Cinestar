package com.da3.MovieTicket.model;

import com.da3.MovieTicket.entity.ConcessionEntity;
import com.da3.MovieTicket.entity.GiftCardEntity;
import lombok.Data;

@Data
public class CartGiftCardUsage {
    private GiftCardEntity giftCard;
    private Long usedAmount;

}
