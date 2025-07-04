package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.RoomEntity;
import com.da3.MovieTicket.entity.SeatEntity;
import com.da3.MovieTicket.repository.RoomRepository;
import com.da3.MovieTicket.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository, RoomRepository roomRepository){
        this.seatRepository = seatRepository;
        this.roomRepository = roomRepository;
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

    public void initializeRoomSeats(Long roomId, int rows, int seatsPerRow) {
        RoomEntity room = roomRepository.findById(roomId).orElseThrow();

        for (int row = 0; row < rows; row++) {
            String rowLabel = String.valueOf((char) ('A' + row));

            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                SeatEntity seat = new SeatEntity();
                seat.setRoom(room);
                seat.setRowLabel(rowLabel);
                seat.setSeatNumber(seatNum);
                seatRepository.save(seat);
            }
        }
    }

    public List<List<SeatEntity>> getSeatLayout(RoomEntity room) {
        List<SeatEntity> seats = seatRepository.findByRoomOrderByRowLabelAscSeatNumberAsc(room);

        return seats.stream()
                .collect(Collectors.groupingBy(SeatEntity::getRowLabel))
                .values()
                .stream()
                .collect(Collectors.toList());
    }
}
