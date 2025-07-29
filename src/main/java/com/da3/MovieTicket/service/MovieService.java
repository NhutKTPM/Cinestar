package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.MovieEntity;
import com.da3.MovieTicket.repository.CinemaRepository;
import com.da3.MovieTicket.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieEntity> getAllMovies() {
        return movieRepository.findAllByOrderByShowDateDesc();
    }

    public void createMovie(MovieEntity movie) {

        movieRepository.save(movie);
    }

    public void updateMovie(MovieEntity movie) {
        movieRepository.save(movie);
    }
    public MovieEntity getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie Id: " + id));
    }

    public List<MovieEntity> getTop8CurrentlyShowingMovies() {
        LocalDate today = LocalDate.now();

        return movieRepository.findTop8ByEnabledTrueAndShowDateLessThanEqualOrderByShowDateDesc(today);
    }

    public List<MovieEntity> getCurrentlyShowingMovies() {
        LocalDate today = LocalDate.now();
        return movieRepository.findByEnabledTrueAndShowDateLessThanEqualOrderByShowDateDesc(today);
    }

    public List<MovieEntity> getTop8UpcomingMovies() {
        LocalDate today = LocalDate.now();

        return movieRepository.findTop8ByEnabledTrueAndShowDateGreaterThanOrderByShowDateAsc(today);
    }

    public List<MovieEntity> getUpcomingMovies() {
        LocalDate today = LocalDate.now();
        return movieRepository.findByEnabledTrueAndShowDateGreaterThanOrderByShowDateAsc(today);
    }

    public List<MovieEntity> getMoviesByGenre(Long genreId){
        return movieRepository.findByGenresGenreIdAndEnabledTrue(genreId);
    }

    public List<MovieEntity> searchMovie(String searchTerm){
        return movieRepository.findByMovieNameContainingIgnoreCaseAndEnabledTrue(searchTerm);
    }
}
