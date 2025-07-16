package com.da3.MovieTicket.mapper;

import com.da3.MovieTicket.dto.CartConcessionItemDTO;
import com.da3.MovieTicket.dto.CartDTO;
import com.da3.MovieTicket.entity.SeatEntity;
import com.da3.MovieTicket.model.Cart;
import com.da3.MovieTicket.model.CartConcessionItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {
    public CartDTO toDto (Cart cart){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setPoster(cart.getShowtime().getMovie().getPoster());
        cartDTO.setMovieName(cart.getShowtime().getMovie().getMovieName());
        cartDTO.setRoomType(cart.getShowtime().getRoom().getRoomType().getRoomTypeName());
        cartDTO.setStartingTime(cart.getShowtime().getStartingTime());
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
        cartDTO.setTotal(cart.getTotal());
        return cartDTO;
    }
    
    public CartConcessionItemDTO toConcessionDto(CartConcessionItem item){
        CartConcessionItemDTO itemDTO = new CartConcessionItemDTO();
        itemDTO.setConcessionName(item.getConcession().getConcessionName());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setTotalPrice(item.getConcession().getPrice() * item.getQuantity());
        
        return itemDTO;
    }
}
