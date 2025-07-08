package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.entity.RoomTypeEntity;
import com.da3.MovieTicket.service.CinemaService;
import com.da3.MovieTicket.service.RegionService;
import com.da3.MovieTicket.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminRoomTypeController {

    private final RoomTypeService roomTypeService;

    @Autowired
    public AdminRoomTypeController(RoomTypeService roomTypeService){

        this.roomTypeService = roomTypeService;
    }

    @GetMapping("/admin/roomType")
    public String adminCinema(Model model){

        List<RoomTypeEntity> roomTypes = roomTypeService.getAllRoomTypes();
        model.addAttribute("roomTypes", roomTypes);

        model.addAttribute("newRoomType", new RoomTypeEntity());

        return "admin/admin-roomType";
    }

    @PostMapping("/admin/roomType/addRoomType")
    public String addRoomType (RoomTypeEntity newRoomType){


        roomTypeService.createRoomType(newRoomType);
        return "redirect:/admin/roomType";
    }
}
