package com.da3.MovieTicket.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartDTO {
    private String poster;
    private String movieName;
    private String roomType;
    private LocalDateTime startingTime;
    private String cinema;
    private String room;
    private String seats;
    private Long totalTicket;
    private List<CartConcessionItemDTO> concessionItems = new ArrayList<>();
    private Long total;
}
