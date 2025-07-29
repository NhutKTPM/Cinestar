package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.*;
import com.da3.MovieTicket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AdminGiftCardImageController {

    private final GiftCardImageService giftCardImageService;
    private final FileService fileService;

    @Autowired
    public AdminGiftCardImageController(GiftCardImageService giftCardImageService,
                                        FileService fileService){
        this.giftCardImageService = giftCardImageService;
        this.fileService = fileService;

    }

    @GetMapping("/admin/gift_card_image")
    public String adminMovie(Model model){
        List<GiftCardImageEntity> giftCardImages = giftCardImageService.getAllGiftCardImages();

        model.addAttribute("giftCardImages", giftCardImages);

        model.addAttribute("newGiftCardImage", new GiftCardImageEntity());

        return "admin/admin-gift-card-image";
    }

    @PostMapping("/admin/gift_card_image/add_gift_card_image")
    public String addGiftCardImage (GiftCardImageEntity giftCardImage,
                            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ){
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileService.handleFileUpload(imageFile);
            giftCardImage.setImage(imageUrl);
        }


        giftCardImageService.createGiftCardImage(giftCardImage);
        return "redirect:/admin/gift_card_image";
    }

}
