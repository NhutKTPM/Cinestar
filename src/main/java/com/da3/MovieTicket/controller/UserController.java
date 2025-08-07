package com.da3.MovieTicket.controller;

import com.da3.MovieTicket.entity.UserEntity;
import com.da3.MovieTicket.repository.UserRepository;
import com.da3.MovieTicket.security.CustomUserDetails;
import com.da3.MovieTicket.service.BillService;
import com.da3.MovieTicket.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BillService billService;
    @Autowired
    private GiftCardService giftCardService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());

        return "user/signup";
    }

    @PostMapping("/process_register")
    public String processRegister(UserEntity user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return "redirect:/";
    }

    @GetMapping("/login")
    String login() {
        return "user/login";
    }

    @GetMapping("/userprofile")
    String userProfile(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
        model.addAttribute("user", currentUser.getUser());
        return "user/user-profile";
    }

    @GetMapping("/userprofile/tickets")
    String userTickets(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
        model.addAttribute("user", currentUser.getUser());
        model.addAttribute("bills", billService.getBillsByUser(currentUser.getUser()));
        return "user/user-tickets";
    }

    @GetMapping("/userprofile/giftcards/received")
    String giftCardReceivedPage(Model model, @AuthenticationPrincipal CustomUserDetails currentUser){
        model.addAttribute("giftCards", giftCardService.getAllGiftCardsOfRecipient(currentUser.getId()));
        return "user/giftcards-received";
    }

    @GetMapping("/userprofile/giftcards/gifted")
    String giftCardGiftedPage(Model model, @AuthenticationPrincipal CustomUserDetails currentUser){
        model.addAttribute("giftCards", giftCardService.getAllGiftCardsOfPurchaser(currentUser.getId()));
        return "user/giftcards-gifted";
    }

}
