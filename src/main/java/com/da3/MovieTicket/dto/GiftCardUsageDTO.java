package com.da3.MovieTicket.dto;

import lombok.Data;

@Data
public class GiftCardUsageDTO {
    private Long giftCardId;
    private String giftCardCode;
    private Long usedAmount;
}
