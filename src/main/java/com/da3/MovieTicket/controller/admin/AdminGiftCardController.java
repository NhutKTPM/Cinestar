package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.GiftCardEntity;
import com.da3.MovieTicket.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminGiftCardController {

    private final GiftCardService giftCardService;

    @Autowired
    public AdminGiftCardController(GiftCardService giftCardService){
        this.giftCardService = giftCardService;
    }

    @GetMapping("/admin/giftCard")
    public String adminGiftCard(Model model){
        List<GiftCardEntity> giftCards = giftCardService.getAllGiftCards();
        model.addAttribute("giftCards", giftCards);

        return "admin/admin-gift-card";
    }
}
