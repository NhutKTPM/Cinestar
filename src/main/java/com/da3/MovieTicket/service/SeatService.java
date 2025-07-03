package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.SeatEntity;
import com.da3.MovieTicket.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {
    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository){
        this.seatRepository = seatRepository;
    }

    public List<SeatEntity> getAllSeats(){
        return seatRepository.findAll();
    }

    public void createSeat(SeatEntity seat){
        seatRepository.save(seat);
    }

    public SeatEntity getSeatById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seat Id: " + id));
    }
}
