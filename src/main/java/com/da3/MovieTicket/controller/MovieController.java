package com.da3.MovieTicket.controller;

import com.da3.MovieTicket.entity.MovieEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import com.da3.MovieTicket.service.MovieService;
import com.da3.MovieTicket.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.da3.MovieTicket.service.RegionService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MovieController {
    private final MovieService movieService;
    private final ShowtimeService showtimeService;
    private final RegionService regionService;


    @Autowired
    public MovieController(MovieService movieService, ShowtimeService showtimeService, RegionService regionService) {
        this.movieService = movieService;
        this.showtimeService = showtimeService;
        this.regionService = regionService;
    }

    @GetMapping("/movie/{id}")
    public String showMovieDetail(@PathVariable("id") long id, Model model) {
        MovieEntity movie = movieService.getMovieById(id);

        if (movie == null) {
            return "redirect:/";
        }

        model.addAttribute("movie", movie);

        List<ShowtimeEntity> showtimes = showtimeService.getShowtimeOfMovie(movie);
        model.addAttribute("showtimes", showtimes);

        List<LocalDate> showDates = showtimeService.getDistinctShowDatesForMovie(movie);
        model.addAttribute("showDates", showDates);

        List<RegionEntity> regions = regionService.getAllRegions();
        model.addAttribute("regions", regions);


        return "movie/movie-detail";
    }
}
