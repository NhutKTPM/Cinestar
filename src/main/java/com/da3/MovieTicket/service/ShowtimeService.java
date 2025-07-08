package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.MovieEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import com.da3.MovieTicket.repository.ShowtimeRepository;
import com.da3.MovieTicket.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;

    @Autowired
    public ShowtimeService(ShowtimeRepository showtimeRepository){
        this.showtimeRepository = showtimeRepository;
    }

    public List<ShowtimeEntity> getAllShowtimes(){
        return showtimeRepository.findAll();
    }

    public void createShowtime(ShowtimeEntity showtime){
        showtimeRepository.save(showtime);
    }

    public ShowtimeEntity getShowtimeById(Long id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid showtime Id: " + id));
    }

    public List<ShowtimeEntity> getShowtimeOfMovie(MovieEntity movie) {
        ;

        return showtimeRepository.findByMovie(movie);
    }


    public List<LocalDate> getDistinctShowDatesForMovie(MovieEntity movie) {
        return showtimeRepository.findDistinctShowDatesByMovie(movie);
    }

    public Map<String, List<ShowtimeEntity>> getShowtimesGroupedByCinema(LocalDate date, CinemaEntity cinema, RegionEntity region) {
        List<ShowtimeEntity> showtimes;
        if (cinema != null) {showtimes = showtimeRepository.findByShowDateAndCinema(date, cinema);}
        else {
            if (region != null) {
                showtimes = showtimeRepository.findByShowDateAndRegion(date, region);
            } else {
                showtimes = showtimeRepository.findByShowDate(date);
            }
        }

        return showtimes.stream()
                .collect(Collectors.groupingBy(
                        showtime -> showtime.getRoom().getCinema().getCinemaName()
                ));
    }
}
