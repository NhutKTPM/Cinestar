package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.ConcessionEntity;
import com.da3.MovieTicket.service.ConcessionService;
import com.da3.MovieTicket.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AdminConcessionController {

    private final ConcessionService concessionService;
    private final FileService fileService;

    @Autowired
    public AdminConcessionController(ConcessionService concessionService,
                                     FileService fileService){

        this.concessionService = concessionService;
        this.fileService = fileService;
    }

    @GetMapping("/admin/concession")
    public String adminConcession(Model model){

        List<ConcessionEntity> concessions = concessionService.getAllConcessions();
        model.addAttribute("concessions", concessions);

        model.addAttribute("newConcession", new ConcessionEntity());

        return "admin/admin-concession";
    }

    @PostMapping("/admin/concession/addConcession")
    public String addConcession (ConcessionEntity newConcession,
                                 @RequestParam(value = "imageFile", required = false) MultipartFile image){

        if (image != null && !image.isEmpty()) {
            String posterUrl = fileService.handleFileUpload(image);
            newConcession.setImage(posterUrl);
        }
        concessionService.createConcession(newConcession);
        return "redirect:/admin/concession";
    }
}
