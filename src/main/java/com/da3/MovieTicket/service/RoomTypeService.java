package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.RoomTypeEntity;
import com.da3.MovieTicket.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;

    @Autowired
    public RoomTypeService(RoomTypeRepository roomTypeRepository){
        this.roomTypeRepository = roomTypeRepository;
    }

    public List<RoomTypeEntity> getAllRoomTypes(){
        return roomTypeRepository.findAll();
    }

    public void createRoomType(RoomTypeEntity roomType){
        roomTypeRepository.save(roomType);
    }

    public RoomTypeEntity getRoomTypeById(Long id) {
        return roomTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room type Id: " + id));
    }

}
