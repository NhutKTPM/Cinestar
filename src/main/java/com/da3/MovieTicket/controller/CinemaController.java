package com.da3.MovieTicket.controller;

import com.da3.MovieTicket.entity.*;
import com.da3.MovieTicket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ShowtimeService showtimeService;


    @GetMapping("/cinema")
    public String moviesPage(Model model,
                             @RequestParam(value = "regionId", required = false) Long regionId,
                             @RequestParam(value = "cinemaId", required = false) Long cinemaId)
    {
        List<RegionEntity> regions = regionService.getAllRegions();
        model.addAttribute("regions", regions);

        if (regionId == null && !regions.isEmpty()) {
            regionId = regions.getFirst().getRegionId();
        }

        model.addAttribute("regionId", regionId);

        List<CinemaEntity> cinemas = cinemaService.getCinemasByRegion(regionService.getRegionById(regionId));
        model.addAttribute("cinemas", cinemas);
        if (cinemaId == null) {
            cinemaId = cinemas.getFirst().getCinemaId();
        }
        model.addAttribute("cinemaId", cinemaId);
        CinemaEntity cinema = cinemaService.getCinemaById(cinemaId);
        model.addAttribute("showDates", showtimeService.getDistinctShowDatesForCinema(cinema));
        return "cinema/cinema";
    }



}
