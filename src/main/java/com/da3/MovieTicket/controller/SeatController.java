package com.da3.MovieTicket.controller;

import com.da3.MovieTicket.entity.MovieEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import com.da3.MovieTicket.service.MovieService;
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

    @Autowired
    public SeatController(ShowtimeService showtimeService){
        this. showtimeService = showtimeService;
    }

    @GetMapping("/cart/{id}/seat")
    public String showSeats(@PathVariable("id") long showtimeId, Model model) {
        ShowtimeEntity showtime = showtimeService.getShowtimeById(showtimeId);



        model.addAttribute("showtime", showtime);



        return "seat/seat-selection";
    }
}
