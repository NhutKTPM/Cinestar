package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository){
        this.cinemaRepository = cinemaRepository;
    }

    public List<CinemaEntity> getAllCinemas(){
        return cinemaRepository.findAll();
    }

    public void createCinema(CinemaEntity cinema){
        cinemaRepository.save(cinema);
    }

    public CinemaEntity getCinemaById(Long id) {
        return cinemaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cinema Id: " + id));
    }
}
