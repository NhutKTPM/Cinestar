package com.da3.MovieTicket.controller;

import com.da3.MovieTicket.entity.Banner;
import com.da3.MovieTicket.entity.MovieEntity;
import com.da3.MovieTicket.service.BannerService;
import com.da3.MovieTicket.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private BannerService bannerService;

    @GetMapping("/")
    public String homePage(Model model) {

        List<MovieEntity> topShowingMovies = movieService.getTop8CurrentlyShowingMovies();
        model.addAttribute("topShowingMovies", topShowingMovies);

        List<MovieEntity> topUpcomingMovies = movieService.getTop8UpcomingMovies();
        model.addAttribute("topUpcomingMovies", topUpcomingMovies);

        List<Banner> banners = bannerService.getAllEnabledBanners();
        model.addAttribute("banners", banners);

        return "index";
    }


}
