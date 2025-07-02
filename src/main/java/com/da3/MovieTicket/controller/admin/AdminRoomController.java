package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.entity.RoomEntity;
import com.da3.MovieTicket.service.CinemaService;
import com.da3.MovieTicket.service.RegionService;
import com.da3.MovieTicket.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminRoomController {

    private final CinemaService cinemaService;
    private final RegionService regionService;
    private final RoomService roomService;

    @Autowired
    public AdminRoomController(CinemaService cinemaService, RegionService regionService, RoomService roomService){

        this.cinemaService = cinemaService;
        this.regionService = regionService;
        this.roomService = roomService;
    }

    @GetMapping("/admin/room")
    public String adminRoom(Model model){
        List<RoomEntity> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        List<CinemaEntity> cinemas = cinemaService.getAllCinemas();
        model.addAttribute("cinemas", cinemas);

        model.addAttribute("newRoom", new RoomEntity());

        return "admin/admin-room";
    }

    @PostMapping("/admin/room/addRoom")
    public String addRoom (RoomEntity newRoom, @RequestParam(value = "cinemaId") Long cinemaId){

        if (cinemaId != null){
            newRoom.setCinema(cinemaService.getCinemaById(cinemaId));
        }

        roomService.createRoom(newRoom);
        return "redirect:/admin/room";
    }
}
