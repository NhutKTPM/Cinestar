package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminRegionController {

    private final RegionService regionService;

    @Autowired
    public AdminRegionController(RegionService regionService){
        this.regionService = regionService;
    }

    @GetMapping("/admin/region")
    public String adminRegion(Model model){
        List<RegionEntity> regions = regionService.getAllRegions();
        model.addAttribute("regions", regions);
        model.addAttribute("newRegion", new RegionEntity());

        return "admin/admin-region";
    }

    @PostMapping("/admin/region/addRegion")
    public String addRegion (RegionEntity newRegion){
        regionService.createRegion(newRegion);
        return "redirect:/admin/region";
    }
}
