package com.da3.MovieTicket.controller.api;

import com.da3.MovieTicket.entity.ShowtimeEntity;
import com.da3.MovieTicket.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class ShowtimeApiController {
    private final ShowtimeService showtimeService;

    @Autowired
    public ShowtimeApiController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @GetMapping("/api/showtimes")
    public Map<String, List<ShowtimeEntity>> getShowtimes(@RequestParam LocalDate date) {
        return showtimeService.getShowtimesByDateGroupedByCinema(date);
    }
}