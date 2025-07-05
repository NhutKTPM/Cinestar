package com.da3.MovieTicket.controller.api;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.service.CinemaService;
import com.da3.MovieTicket.service.RegionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CinemaApiController {
    
    private final CinemaService cinemaService;
    private final RegionService regionService;

    public CinemaApiController(CinemaService cinemaService, RegionService regionService) {
        this.cinemaService = cinemaService;
        this.regionService = regionService;
    }

    @GetMapping("/cinemas")
    public List<CinemaEntity> getAllCinemas() {
        return cinemaService.getAllCinemas();
    }

    @GetMapping("/cinemas/region/{regionId}")
    public List<CinemaEntity> getCinemasByRegion(@PathVariable Long regionId) {
        RegionEntity region = regionService.getRegionById(regionId);
        return cinemaService.getCinemasByRegion(region);
    }
}
