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
public class MovieController {
    private final MovieService movieService;
    private final ShowtimeService showtimeService;

    @Autowired
    public MovieController(MovieService movieService, ShowtimeService showtimeService){
        this.movieService = movieService;
        this. showtimeService = showtimeService;
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


        return "movie/movie-detail";
    }
}
