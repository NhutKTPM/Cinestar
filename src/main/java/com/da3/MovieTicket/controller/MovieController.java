package com.da3.MovieTicket.controller;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.MovieEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import com.da3.MovieTicket.service.CinemaService;
import com.da3.MovieTicket.service.MovieService;
import com.da3.MovieTicket.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.da3.MovieTicket.service.RegionService;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class MovieController {
    private final MovieService movieService;
    private final ShowtimeService showtimeService;
    private final RegionService regionService;
    private final CinemaService cinemaService;


    @Autowired
    public MovieController(MovieService movieService,
                           ShowtimeService showtimeService,
                           RegionService regionService,
                           CinemaService cinemaService) {
        this.movieService = movieService;
        this.showtimeService = showtimeService;
        this.regionService = regionService;
        this.cinemaService = cinemaService;

    }

    @GetMapping("/movie/{id}")
    public String showMovieDetail(@PathVariable("id") long id, Model model,
                                  @RequestParam(value = "date", required = false) LocalDate date,
                                  @RequestParam(value = "cinemaId", required = false) Long cinemaId,
                                  @RequestParam(value = "regionId", required = false) Long regionId) {
        MovieEntity movie = movieService.getMovieById(id);

        if (movie == null) {
            return "redirect:/";
        }

        model.addAttribute("movie", movie);

//        List<ShowtimeEntity> showtimes = showtimeService.getShowtimeOfMovie(movie);
//        model.addAttribute("showtimes", showtimes);

        List<LocalDate> showDates = showtimeService.getDistinctShowDatesForMovie(movie);
        model.addAttribute("showDates", showDates);

        if (date == null && !showDates.isEmpty()) { date = showDates.getFirst();}

        CinemaEntity cinema = null;
        RegionEntity region = null;
        if (cinemaId != null) { cinema = cinemaService.getCinemaById(cinemaId);}
        if (regionId != null) { region = regionService.getRegionById(regionId);}
        Map<String, List<ShowtimeEntity>> showtimesByCinema = showtimeService.getShowtimesGroupedByCinema(date, cinema, region);
        model.addAttribute("showtimesByCinema", showtimesByCinema);



        List<RegionEntity> regions = regionService.getAllRegions();
        model.addAttribute("regions", regions);


        return "movie/movie-detail";
    }
}
