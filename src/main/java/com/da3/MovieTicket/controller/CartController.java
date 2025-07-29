package com.da3.MovieTicket.controller;

import com.da3.MovieTicket.entity.*;
import com.da3.MovieTicket.mapper.CartMapper;
import com.da3.MovieTicket.model.CartConcessionItem;
import com.da3.MovieTicket.security.CustomUserDetails;
import com.da3.MovieTicket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ShowtimeService showtimeService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private ConcessionService concessionService;
    @Autowired
    private BillService billService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private BillConcessionItemService billConcessionItemService;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private GiftCardService giftCardService;



    @GetMapping("/cart/{id}/seat")
    public String showSeats(@PathVariable("id") long showtimeId, Model model) {
        ShowtimeEntity showtime = showtimeService.getShowtimeById(showtimeId);



        model.addAttribute("showtime", showtime);

        List<List<SeatEntity>> seatLayout = seatService.getSeatLayout(showtime.getRoom());

        model.addAttribute("seatLayout", seatLayout);

        cartService.clearCart();

        cartService.getCart().setShowtime(showtime);
        model.addAttribute("cart", cartMapper.toDto(cartService.getCart()));

        return "cart/seat-selection";
    }

    @GetMapping("/cart/{id}/concession")
    public String showConcession(@PathVariable("id") long showtimeId, Model model) {
        ShowtimeEntity showtime = showtimeService.getShowtimeById(showtimeId);



        model.addAttribute("showtime", showtime);

        List<ConcessionEntity> concessions = concessionService.getAllConcessions();
        model.addAttribute("concessions", concessions);

        model.addAttribute("cart", cartMapper.toDto(cartService.getCart()));


        return "cart/concession-selection";
    }

    @GetMapping("/cart/{id}/checkoutPage")
    public String showCheckoutPage(@PathVariable("id") long showtimeId, Model model,
                                   @AuthenticationPrincipal CustomUserDetails currentUser) {
        ShowtimeEntity showtime = showtimeService.getShowtimeById(showtimeId);



        model.addAttribute("showtime", showtime);

        model.addAttribute("cart", cartMapper.toDto(cartService.getCart()));

        model.addAttribute("giftCards", giftCardService.getAllGiftCardsOfRecipient(currentUser.getId()));

        return "cart/checkout";
    }

    @GetMapping("/cart/{id}/checkout")
    public String processCheckout(@PathVariable("id") long showtimeId,
                                  @AuthenticationPrincipal CustomUserDetails currentUser)
    {
        BillEntity newBill = new BillEntity();
        newBill.setUser(currentUser.getUser());
        newBill.setShowtime(showtimeService.getShowtimeById(showtimeId));

        // Save the bill first to get the ID
        BillEntity savedBill = billService.createBill(newBill);

        // Create tickets for selected seats
        for (SeatEntity seat : cartService.getCart().getSelectedSeats()) {
            TicketEntity ticket = new TicketEntity();
            ticket.setBill(savedBill);
            ticket.setSeat(seat);
            ticketService.createTicket(ticket);
        }

        // Create bill concession items
        for (CartConcessionItem cartItem : cartService.getCart().getConcessionItems()) {
            BillConcessionItemEntity billConcessionItemItem = new BillConcessionItemEntity();
            billConcessionItemItem.setBill(savedBill);
            billConcessionItemItem.setConcession(cartItem.getConcession());
            billConcessionItemItem.setQuantity(cartItem.getQuantity());
            billConcessionItemService.createBillConcessionItem(billConcessionItemItem);
        }

        // Clear the cart after successful checkout
        cartService.clearCart();

        // Redirect to the bill detail page
        return "redirect:/bill/" + savedBill.getBillId();

    }


    @GetMapping("/cart/getCart")
    @ResponseBody
    public ResponseEntity<?> getCart() {
        return ResponseEntity.ok(cartMapper.toDto(cartService.getCart()));
    }


    @PostMapping("/cart/seats/add/{seatId}")
    @ResponseBody
    public ResponseEntity<?> addSeat(@PathVariable Long seatId) {
        SeatEntity seat = seatService.getSeatById(seatId);
        cartService.addSeat(seat);
        return ResponseEntity.ok(cartMapper.toDto(cartService.getCart()));
    }

    @PostMapping("/cart/seats/remove/{seatId}")
    @ResponseBody
    public ResponseEntity<?> removeSeat(@PathVariable Long seatId) {
        SeatEntity seat = seatService.getSeatById(seatId);
        cartService.removeSeat(seat);
        return ResponseEntity.ok(cartMapper.toDto(cartService.getCart()));
    }

    @PostMapping("/cart/concessions/add/{concessionId}")
    @ResponseBody
    public ResponseEntity<?> addConcession(@PathVariable Long concessionId) {
        ConcessionEntity concession = concessionService.getConcessionById(concessionId);
        cartService.addConcession(concession);
        return ResponseEntity.ok(cartMapper.toDto(cartService.getCart()));
    }

    @PostMapping("/cart/concessions/update/{concessionId}")
    @ResponseBody
    public ResponseEntity<?> updateConcessionQuantity(
            @PathVariable Long concessionId,
            @RequestParam int quantity) {
        cartService.updateConcessionQuantity(concessionId, quantity);
        return ResponseEntity.ok(cartMapper.toDto(cartService.getCart()));
    }


    @PostMapping("/cart/giftcards/use/{giftCardId}")
    @ResponseBody
    public ResponseEntity<?> useGiftCard(@PathVariable Long giftCardId){
        cartService.addGiftCard(giftCardService.getGiftCardById(giftCardId));
        return ResponseEntity.ok(cartMapper.toDto(cartService.getCart()));
    }
}
