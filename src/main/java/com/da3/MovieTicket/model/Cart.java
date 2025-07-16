package com.da3.MovieTicket.model;

import com.da3.MovieTicket.entity.ConcessionEntity;
import com.da3.MovieTicket.entity.SeatEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private ShowtimeEntity showtime;
    private List<SeatEntity> selectedSeats = new ArrayList<>();
    private List<CartConcessionItem> concessionItems = new ArrayList<>();

    public Long getTotal() {
        Long total = 0L;
        // Add seats total
        for (SeatEntity seat : selectedSeats) {
            total = total + seat.getSeatType().getPrice();
        }
        // Add concessions total
        for (CartConcessionItem item : concessionItems) {
            total = total + (item.getConcession().getPrice()
                    * (long) item.getQuantity());
        }
        return total;
    }

    public Long getTotalTicket(){
        Long total = 0L;
        // Add seats total
        for (SeatEntity seat : selectedSeats) {
            total = total + seat.getSeatType().getPrice();
        }
        return total;
    }
}