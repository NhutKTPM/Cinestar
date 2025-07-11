package com.da3.MovieTicket.model;

import com.da3.MovieTicket.entity.ConcessionEntity;
import lombok.Data;

@Data
public class CartConcessionItem {
    private ConcessionEntity concession;
    private int quantity;

}
