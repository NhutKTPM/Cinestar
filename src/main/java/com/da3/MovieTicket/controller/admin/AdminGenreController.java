package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.GenreEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.service.GenreService;
import com.da3.MovieTicket.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminGenreController {

    private final GenreService genreService;

    @Autowired
    public AdminGenreController(GenreService genreService, RegionService regionService){

        this.genreService = genreService;
    }

    @GetMapping("/admin/genre")
    public String adminGenre(Model model){
        List<GenreEntity> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres);

        model.addAttribute("newGenre", new GenreEntity());

        return "admin/admin-genre";
    }

    @PostMapping("/admin/genre/addGenre")
    public String addGenre (GenreEntity newGenre, @RequestParam(value = "regionId", required = false) Long regionId){


        genreService.createGenre(newGenre);
        return "redirect:/admin/genre";
    }
}
