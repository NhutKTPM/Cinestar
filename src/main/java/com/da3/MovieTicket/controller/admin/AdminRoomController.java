package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.entity.RoomEntity;
import com.da3.MovieTicket.entity.RoomTypeEntity;
import com.da3.MovieTicket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminRoomController {

    private final CinemaService cinemaService;
    private final SeatService seatService;
    private final RoomService roomService;
    private final RoomTypeService roomTypeService;

    @Autowired
    public AdminRoomController(CinemaService cinemaService, SeatService seatService, RoomService roomService,
                               RoomTypeService roomTypeService){

        this.cinemaService = cinemaService;
        this.seatService = seatService;
        this.roomService = roomService;
        this.roomTypeService = roomTypeService;
    }

    @GetMapping("/admin/room")
    public String adminRoom(Model model){
        List<RoomEntity> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        List<CinemaEntity> cinemas = cinemaService.getAllCinemas();
        model.addAttribute("cinemas", cinemas);
        List<RoomTypeEntity> roomTypes = roomTypeService.getAllRoomTypes();
        model.addAttribute("roomTypes", roomTypes);

        model.addAttribute("newRoom", new RoomEntity());

        return "admin/admin-room";
    }

    @PostMapping("/admin/room/addRoom")
    public String addRoom (RoomEntity newRoom, @RequestParam(value = "cinemaId") Long cinemaId, @RequestParam(value = "roomTypeId") Long roomTypeId){

        if (cinemaId != null){
            newRoom.setCinema(cinemaService.getCinemaById(cinemaId));
        }

        if (roomTypeId != null){
            newRoom.setRoomType(roomTypeService.getRoomTypeById(roomTypeId));
        }

        RoomEntity room = roomService.createRoom(newRoom);

        seatService.initializeRoomSeats(room.getRoomId(), 10, 15 );
        return "redirect:/admin/room";
    }

    @GetMapping("/admin/room/initializeRoomSeats/{id}")
    public String initializeRoomSeats (@PathVariable("id") long roomId){
        RoomEntity room = roomService.getRoomById(roomId);
        if (room == null){
            return "redirect:/";
        }
        seatService.initializeRoomSeats(room.getRoomId(), 10, 15 );
        return "redirect:/admin/room";
    }
}
