package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.MovieEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.entity.RoomEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import com.da3.MovieTicket.repository.RegionRepository;
import com.da3.MovieTicket.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public List<RoomEntity> getAllRooms (){
        return roomRepository.findAll();
    }

    public RoomEntity createRoom(RoomEntity room){
        return roomRepository.save(room);
    }

    public RoomEntity getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room Id: " + id));
    }

}
