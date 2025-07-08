package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.RoomEntity;
import com.da3.MovieTicket.entity.SeatTypeEntity;
import com.da3.MovieTicket.repository.RoomRepository;
import com.da3.MovieTicket.repository.SeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatTypeService {
    private final SeatTypeRepository seatTypeRepository;

    @Autowired
    public SeatTypeService(SeatTypeRepository seatTypeRepository){
        this.seatTypeRepository = seatTypeRepository;
    }

    public List<SeatTypeEntity> getAllSeatTypes (){
        return seatTypeRepository.findAll();
    }

    public SeatTypeEntity createSeatType(SeatTypeEntity seatType){
        return seatTypeRepository.save(seatType);
    }

    public SeatTypeEntity getSeatTypeById(Long id) {
        return seatTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seat type Id: " + id));
    }

}
