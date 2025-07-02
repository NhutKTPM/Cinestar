package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.service.CinemaService;
import com.da3.MovieTicket.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminCinemaController {

    private final CinemaService cinemaService;
    private final RegionService regionService;

    @Autowired
    public AdminCinemaController(CinemaService cinemaService, RegionService regionService){

        this.cinemaService = cinemaService;
        this.regionService = regionService;
    }

    @GetMapping("/admin/cinema")
    public String adminCinema(Model model){
        List<CinemaEntity> cinemas = cinemaService.getAllCinemas();
        model.addAttribute("cinemas", cinemas);
        List<RegionEntity> regions = regionService.getAllRegions();
        model.addAttribute("regions", regions);

        model.addAttribute("newCinema", new CinemaEntity());

        return "admin/admin-cinema";
    }

    @PostMapping("/admin/cinema/addCinema")
    public String addCinema (CinemaEntity newCinema, @RequestParam(value = "regionId", required = false) Long regionId){

        if (regionId != null){
            newCinema.setRegion(regionService.getRegionById(regionId));
        }

        cinemaService.createCinema(newCinema);
        return "redirect:/admin/cinema";
    }
}
