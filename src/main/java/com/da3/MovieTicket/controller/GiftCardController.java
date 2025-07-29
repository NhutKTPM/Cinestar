package com.da3.MovieTicket.controller;

import com.da3.MovieTicket.entity.GiftCardEntity;
import com.da3.MovieTicket.entity.GiftCardImageEntity;
import com.da3.MovieTicket.entity.UserEntity;
import com.da3.MovieTicket.security.CustomUserDetails;
import com.da3.MovieTicket.service.GiftCardImageService;
import com.da3.MovieTicket.service.GiftCardService;
import com.da3.MovieTicket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GiftCardController {
    @Autowired
    private GiftCardService giftCardService;
    @Autowired
    private GiftCardImageService giftCardImageService;
    @Autowired
    private UserService userService;

    @GetMapping("/giftcard")
    public String giftCardPage(Model model){
        model.addAttribute("giftCardImages", giftCardImageService.getAllGiftCardImages());

        return "giftcard/giftcard";
    }

    @GetMapping("/giftcard/checkout")
    public String giftCardCheckoutPage(Model model,
                                       @AuthenticationPrincipal CustomUserDetails currentUser,
                                       @RequestParam(value = "imageId", required = false) Long imageId,
                                       @RequestParam(value = "recipientEmail", required = false) String recipientEmail){
        GiftCardImageEntity giftCardImage = giftCardImageService.getGiftCardImageById(imageId);
        model.addAttribute("giftCardImage", giftCardImage);
        UserEntity recipient = null;
        if (recipientEmail != null){
            recipient = userService.getUserByEmail(recipientEmail);
        }
        model.addAttribute("recipient", recipient);

        return "giftcard/giftcard-checkout";
    }

    @PostMapping("giftcard/newgiftcard")
    public String createNewGiftCard(@RequestParam(value = "imageId") Long imageId,
                                    @RequestParam(value = "recipientEmail") String recipientEmail,
                                    @RequestParam(value = "initialBalance") Long initialBalance,
                                    @AuthenticationPrincipal CustomUserDetails currentUser){
        GiftCardEntity newGiftCard = new GiftCardEntity();
        newGiftCard.setGiftCardImage(giftCardImageService.getGiftCardImageById(imageId));
        newGiftCard.setPurchaser(currentUser.getUser());
        newGiftCard.setRecipient(userService.getUserByEmail(recipientEmail));
        newGiftCard.setInitialBalance(initialBalance);
        newGiftCard.setCurrentBalance(initialBalance);
        giftCardService.createGiftCard(newGiftCard);
        return "redirect:/";
    }
}
