package com.da3.MovieTicket.controller.api;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import com.da3.MovieTicket.repository.CinemaRepository;
import com.da3.MovieTicket.service.CinemaService;
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
    private final CinemaService cinemaService;

    @Autowired
    public ShowtimeApiController(ShowtimeService showtimeService, CinemaService cinemaService) {
        this.showtimeService = showtimeService;
        this.cinemaService = cinemaService;
    }

//    @GetMapping("/api/showtimes")
//    public Map<String, List<ShowtimeEntity>> getShowtimes(@RequestParam LocalDate date, @RequestParam Long cinemaId) {
//        CinemaEntity cinema = null;
//        if (cinemaId != null) { cinema = cinemaService.getCinemaById(cinemaId); }
//        return showtimeService.getShowtimesGroupedByCinema(date, cinema);
//    }
}