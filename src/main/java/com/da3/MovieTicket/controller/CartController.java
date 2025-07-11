package com.da3.MovieTicket.controller;

import com.da3.MovieTicket.entity.ConcessionEntity;
import com.da3.MovieTicket.entity.SeatEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import com.da3.MovieTicket.service.CartService;
import com.da3.MovieTicket.service.ConcessionService;
import com.da3.MovieTicket.service.SeatService;
import com.da3.MovieTicket.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {
    private final CartService cartService;
    private final ShowtimeService showtimeService;
    private final SeatService seatService;
    private final ConcessionService concessionService;

    @Autowired
    public CartController(ShowtimeService showtimeService, SeatService seatService,
                          ConcessionService concessionService,
                          CartService cartService){
        this. showtimeService = showtimeService;
        this.seatService = seatService;
        this.concessionService = concessionService;
        this.cartService = cartService;
    }

    @GetMapping("/cart/{id}/seat")
    public String showSeats(@PathVariable("id") long showtimeId, Model model) {
        ShowtimeEntity showtime = showtimeService.getShowtimeById(showtimeId);



        model.addAttribute("showtime", showtime);

        List<List<SeatEntity>> seatLayout = seatService.getSeatLayout(showtime.getRoom());

        model.addAttribute("seatLayout", seatLayout);


        return "cart/seat-selection";
    }

    @GetMapping("/cart/{id}/concession")
    public String showConcession(@PathVariable("id") long showtimeId, Model model) {
        ShowtimeEntity showtime = showtimeService.getShowtimeById(showtimeId);



        model.addAttribute("showtime", showtime);

//        List<List<SeatEntity>> seatLayout = seatService.getSeatLayout(showtime.getRoom());
//
//        model.addAttribute("seatLayout", seatLayout);

        List<ConcessionEntity> concessions = concessionService.getAllConcessions();
        model.addAttribute("concessions", concessions);



        return "cart/concession-selection";
    }

    @GetMapping("/cart/{id}/checkoutPage")
    public String showCheckoutPage(@PathVariable("id") long showtimeId, Model model) {
        ShowtimeEntity showtime = showtimeService.getShowtimeById(showtimeId);



        model.addAttribute("showtime", showtime);



        return "cart/checkout";
    }





    @PostMapping("/cart/seats/add/{seatId}")
    @ResponseBody
    public ResponseEntity<?> addSeat(@PathVariable Long seatId) {
        SeatEntity seat = seatService.getSeatById(seatId);
        cartService.addSeat(seat);
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/cart/concessions/add/{concessionId}")
    @ResponseBody
    public ResponseEntity<?> addConcession(@PathVariable Long concessionId) {
        ConcessionEntity concession = concessionService.getConcessionById(concessionId);
        cartService.addConcession(concession);
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/cart/concessions/update/{concessionId}")
    @ResponseBody
    public ResponseEntity<?> updateConcessionQuantity(
            @PathVariable Long concessionId,
            @RequestParam int quantity) {
        cartService.updateConcessionQuantity(concessionId, quantity);
        return ResponseEntity.ok(cartService.getCart());
    }

}
