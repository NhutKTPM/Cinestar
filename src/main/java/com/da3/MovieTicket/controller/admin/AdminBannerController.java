package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.Banner;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.service.BannerService;
import com.da3.MovieTicket.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class AdminBannerController {

    @Autowired
    private BannerService bannerService;
    @Autowired
    private FileService fileService;



    @GetMapping("/admin/banner")
    public String adminBanner(Model model){
        List<Banner> banners = bannerService.getAllBanners();
        model.addAttribute("banners", banners);
        model.addAttribute("newBanner", new Banner());

        return "admin/admin-banner";
    }

    @PostMapping("/admin/banner/addBanner")
    public String addBanner (Banner newBanner,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileService.handleFileUpload(imageFile);
            newBanner.setBanner(imageUrl);
        }
        bannerService.createBanner(newBanner);
        return "redirect:/admin/banner";
    }

    @PostMapping("/admin/banner/{id}/disable")
    public String disableBanner (@PathVariable Long id){
        bannerService.disableBanner(id);
        return "redirect:/admin/banner";
    }
}
