package com.da3.MovieTicket.controller;

import com.da3.MovieTicket.entity.MovieEntity;
import com.da3.MovieTicket.entity.SeatEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import com.da3.MovieTicket.service.MovieService;
import com.da3.MovieTicket.service.SeatService;
import com.da3.MovieTicket.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class SeatController {
    private final ShowtimeService showtimeService;
    private final SeatService seatService;

    @Autowired
    public SeatController(ShowtimeService showtimeService, SeatService seatService){
        this. showtimeService = showtimeService;
        this.seatService = seatService;
    }

    @GetMapping("/cart/{id}/seat")
    public String showSeats(@PathVariable("id") long showtimeId, Model model) {
        ShowtimeEntity showtime = showtimeService.getShowtimeById(showtimeId);



        model.addAttribute("showtime", showtime);

        List<List<SeatEntity>> seatLayout = seatService.getSeatLayout(showtime.getRoom());

        model.addAttribute("seatLayout", seatLayout);


        return "seat/seat-selection";
    }
}
