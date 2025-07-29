package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.GenreEntity;
import com.da3.MovieTicket.entity.MovieEntity;
import com.da3.MovieTicket.entity.RoomEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import com.da3.MovieTicket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminMovieController {

    private final MovieService movieService;
    private final FileService fileService;
    private final GenreService genreService;
    private final ShowtimeService showtimeService;
    private final RoomService roomService;

    @Autowired
    public AdminMovieController(MovieService movieService, FileService fileService, GenreService genreService,
                                ShowtimeService showtimeService, RoomService roomService){

        this.movieService = movieService;
        this.fileService = fileService;
        this.genreService = genreService;
        this.showtimeService = showtimeService;
        this.roomService = roomService;
    }

    @GetMapping("/admin/movie")
    public String adminMovie(Model model){
        List<MovieEntity> movies = movieService.getAllMovies();

        List<GenreEntity> allGenres = genreService.getAllGenres();
        model.addAttribute("movies", movies);

        model.addAttribute("allGenres", allGenres);
        model.addAttribute("newMovie", new MovieEntity());

        return "admin/admin-movie";
    }

    @PostMapping("/admin/movie/addMovie")
    public String addMovie (MovieEntity newMovie,
                            @RequestParam(value = "posterFile", required = false) MultipartFile poster
    ){
        if (poster != null && !poster.isEmpty()) {
            String posterUrl = fileService.handleFileUpload(poster);
            newMovie.setPoster(posterUrl);
        }


        movieService.createMovie(newMovie);
        return "redirect:/admin/movie";
    }

    @GetMapping("/admin/movie/{id}")
    public String showAdminMovieDetail(@PathVariable("id") long id, Model model) {
        MovieEntity movie = movieService.getMovieById(id);

        if (movie == null) {
            return "redirect:/";
        }

        List<RoomEntity> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);

        model.addAttribute("movie", movie);
        model.addAttribute("newShowtime", new ShowtimeEntity());

        List<ShowtimeEntity> showtimes = showtimeService.getShowtimeOfMovie(movie);
        model.addAttribute("showtimes", showtimes);

        List<GenreEntity> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres);

        System.out.println(movie.getGenres().size());

        return "admin/admin-movie-detail";
    }

    @PostMapping("/admin/movie/{id}/addShowtime")
    public String addShowtimeForMovie (ShowtimeEntity newShowtime, @PathVariable("id") long movieId,
                                       @RequestParam(value = "roomId", required = false) Long roomId
    ){
        newShowtime.setMovie(movieService.getMovieById(movieId));
        newShowtime.setRoom(roomService.getRoomById(roomId));
        showtimeService.createShowtime(newShowtime);
        return "redirect:/admin/movie/" + newShowtime.getMovie().getMovieId();
    }

    @PostMapping("/admin/movie/{id}/addGenre")
    public String addGenreToMovie (@PathVariable("id") long movieId, @RequestParam(value = "genreId") Long genreId){
        MovieEntity movie = movieService.getMovieById(movieId);
        GenreEntity genre = genreService.getGenreById(genreId);
        movie.getGenres().add(genre);
        movieService.updateMovie(movie);
        return "redirect:/admin/movie/" + movieId;
    }
}
