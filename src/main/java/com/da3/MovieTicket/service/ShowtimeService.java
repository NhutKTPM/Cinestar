package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.MovieEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import com.da3.MovieTicket.repository.ShowtimeRepository;
import com.da3.MovieTicket.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
