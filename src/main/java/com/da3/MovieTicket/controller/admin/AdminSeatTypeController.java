package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.RoomTypeEntity;
import com.da3.MovieTicket.entity.SeatEntity;
import com.da3.MovieTicket.entity.SeatTypeEntity;
import com.da3.MovieTicket.service.RoomTypeService;
import com.da3.MovieTicket.service.SeatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminSeatTypeController {

    private final SeatTypeService seatTypeService;

    @Autowired
    public AdminSeatTypeController(SeatTypeService seatTypeService){

        this.seatTypeService = seatTypeService;
    }

    @GetMapping("/admin/seatType")
    public String adminSeatType(Model model){

        List<SeatTypeEntity> seatTypes = seatTypeService.getAllSeatTypes();
        model.addAttribute("seatTypes", seatTypes);

        model.addAttribute("newSeatType", new SeatTypeEntity());

        return "admin/admin-seatType";
    }

    @PostMapping("/admin/seatType/addSeatType")
    public String addSeatType (SeatTypeEntity newSeatType){


        seatTypeService.createSeatType(newSeatType);
        return "redirect:/admin/seatType";
    }
}
