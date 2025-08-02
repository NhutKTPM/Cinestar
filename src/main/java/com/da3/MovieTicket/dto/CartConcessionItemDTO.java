package com.da3.MovieTicket.dto;

import lombok.Data;

@Data
public class CartConcessionItemDTO {
    private String concessionName;
    private Long id;
    private int quantity;
    private Long totalPrice;
}
