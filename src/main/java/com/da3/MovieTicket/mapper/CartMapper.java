package com.da3.MovieTicket.mapper;

import com.da3.MovieTicket.dto.CartConcessionItemDTO;
import com.da3.MovieTicket.dto.CartDTO;
import com.da3.MovieTicket.dto.GiftCardUsageDTO;
import com.da3.MovieTicket.entity.*;
import com.da3.MovieTicket.model.Cart;
import com.da3.MovieTicket.model.CartConcessionItem;
import com.da3.MovieTicket.model.CartGiftCardUsage;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {
    public CartDTO toDto (Cart cart){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setPoster(cart.getShowtime().getMovie().getPoster());
        cartDTO.setMovieName(cart.getShowtime().getMovie().getMovieName());
        cartDTO.setRating(cart.getShowtime().getMovie().getRating());
        cartDTO.setRoomType(cart.getShowtime().getRoom().getRoomType().getRoomTypeName());
        cartDTO.setStartingTime(cart.getShowtime().getStartingTime());
        cartDTO.setEndingTime(cart.getShowtime().getStartingTime().plusMinutes(cart.getShowtime().getMovie().getLength() != null ? cart.getShowtime().getMovie().getLength() : 0));
        cartDTO.setCinema(cart.getShowtime().getRoom().getCinema().getCinemaName());
        cartDTO.setRoom(cart.getShowtime().getRoom().getRoomName());
        cartDTO.setSeats(cart.getSelectedSeats().stream()
                .map(SeatEntity::getSeatName)
                .sorted()
                .collect(Collectors.joining(", ")));
        cartDTO.setTotalTicket(cart.getTotalTicket());
        cartDTO.setConcessionItems(cart.getConcessionItems().stream()
                .map(this::toConcessionDto)
                .collect(Collectors.toList()));
        cartDTO.setGiftCardUsages(cart.getGiftCardUsages().stream()
                .map(this::toBillGiftCardUsageDto)
                .collect(Collectors.toList()));
        cartDTO.setTotal(cart.getTotal());
        return cartDTO;
    }
    
    public CartConcessionItemDTO toConcessionDto(CartConcessionItem item){
        CartConcessionItemDTO itemDTO = new CartConcessionItemDTO();
        itemDTO.setConcessionName(item.getConcession().getConcessionName());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setTotalPrice(item.getConcession().getPrice() * item.getQuantity());
        itemDTO.setId(item.getConcession().getConcessionId());
        return itemDTO;
    }

    public GiftCardUsageDTO toBillGiftCardUsageDto(CartGiftCardUsage giftCardUsage){
        GiftCardUsageDTO usageDTO = new GiftCardUsageDTO();
        usageDTO.setGiftCardId(giftCardUsage.getGiftCard().getGiftCardId());
        usageDTO.setGiftCardCode(giftCardUsage.getGiftCard().getGiftCardCode());
        usageDTO.setUsedAmount(giftCardUsage.getUsedAmount());
        return usageDTO;
    }


    public CartDTO toDto(BillEntity bill) {
        CartDTO cartDTO = new CartDTO();

        // Map movie details
        ShowtimeEntity showtime = bill.getShowtime();
        cartDTO.setPoster(showtime.getMovie().getPoster());
        cartDTO.setMovieName(showtime.getMovie().getMovieName());
        cartDTO.setRating(showtime.getMovie().getRating());
        cartDTO.setRoomType(showtime.getRoom().getRoomType().getRoomTypeName());
        cartDTO.setStartingTime(showtime.getStartingTime());
        cartDTO.setEndingTime(bill.getShowtime().getStartingTime().plusMinutes(bill.getShowtime().getMovie().getLength() != null ? bill.getShowtime().getMovie().getLength() : 0));
        cartDTO.setCinema(showtime.getRoom().getCinema().getCinemaName());
        cartDTO.setRoom(showtime.getRoom().getRoomName());

        // Map seats from tickets
        cartDTO.setSeats(bill.getTickets().stream()
                .map(ticket -> ticket.getSeat().getSeatName())
                .sorted()
                .collect(Collectors.joining(", ")));


        cartDTO.setTotalTicket(bill.getTotalTicket());

        // Map concession items
        cartDTO.setConcessionItems(bill.getConcessionItems().stream()
                .map(this::toBillConcessionDto)
                .collect(Collectors.toList()));

        // Map gift card usage
        cartDTO.setGiftCardUsages(bill.getGiftCardUsages().stream()
                .map(this::toBillGiftCardUsageDto)
                .collect(Collectors.toList()));

        cartDTO.setTotal(bill.getTotal());

        return cartDTO;
    }

    private CartConcessionItemDTO toBillConcessionDto(BillConcessionItemEntity item) {
        CartConcessionItemDTO itemDTO = new CartConcessionItemDTO();
        itemDTO.setConcessionName(item.getConcession().getConcessionName());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setTotalPrice(item.getConcession().getPrice() * item.getQuantity());
        return itemDTO;
    }

    private GiftCardUsageDTO toBillGiftCardUsageDto(BillGiftCardUsageEntity giftCardUsage){
        GiftCardUsageDTO usageDTO = new GiftCardUsageDTO();
        usageDTO.setGiftCardId(giftCardUsage.getGiftCard().getGiftCardId());
        usageDTO.setGiftCardCode(giftCardUsage.getGiftCard().getGiftCardCode());
        usageDTO.setUsedAmount(giftCardUsage.getUsedAmount());
        return usageDTO;
    }

}
