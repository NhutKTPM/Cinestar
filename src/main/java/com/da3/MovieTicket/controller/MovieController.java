package com.da3.MovieTicket.controller;

import com.da3.MovieTicket.entity.*;
import com.da3.MovieTicket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private ShowtimeService showtimeService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private BillService billService;


    


    @GetMapping("/movie")
    public String moviesPage(Model model,
                             @RequestParam(value = "showingOrUpcoming", required = false) String showingOrUpcoming,
                             @RequestParam(value = "genreId", required = false) Long genreId)
    {
        List<MovieEntity> movies = new ArrayList<>();

        if (showingOrUpcoming != null) {
            if (showingOrUpcoming.equals("showing")) {
                if (genreId != null) {
                    movies = movieService.getCurrentlyShowingMoviesByGenre(genreId);
                } else {
                    movies = movieService.getCurrentlyShowingMovies();
                }

            } else {
                if (genreId != null) {
                    movies = movieService.getUpcomingMoviesByGenre(genreId);
                } else {
                    movies = movieService.getUpcomingMovies();
                }
            }
        }
        else if (genreId != null) {
            movies = movieService.getMoviesByGenre(genreId);
        }
        else {
            movies = movieService.getCurrentlyShowingMovies();
        }
        model.addAttribute("movies", movies);
        List<GenreEntity> allGenres = genreService.getAllGenres();
        model.addAttribute("genres", allGenres);

        return "movie/movies";
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
        Map<String, Map<String, List<ShowtimeEntity>>> showtimesByCinema = showtimeService.getShowtimesGroupedByCinema(movie, date, cinema, region);
        model.addAttribute("showtimesByCinema", showtimesByCinema);



        List<RegionEntity> regions = regionService.getAllRegions();
        model.addAttribute("regions", regions);

        model.addAttribute("top3Movies", movieService.getTop3CurrentlyShowingMovies());

        List<BillEntity> bills = billService.getBillsByMovie(movie);
        model.addAttribute("bills", bills);
        if (!bills.isEmpty()) {
            Double averageRating = bills.stream()
                    .filter(bill -> bill.getRating() != null)
                    .mapToDouble(BillEntity::getRating)
                    .average()
                    .orElse(0.0);
            if (averageRating > 0.0) {
                model.addAttribute("averageRating", averageRating);
            }
        }

        return "movie/movie-detail";
    }

    @GetMapping("/movie/search")
    public String searchMovie(Model model,
                              @RequestParam(value = "searchTerm", required = false) String searchTerm
                              ){
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "redirect:/";
        }
        List<MovieEntity> movies = movieService.searchMovie(searchTerm);
        model.addAttribute("movies", movies);
        return "movie/search-result";
    }
}
