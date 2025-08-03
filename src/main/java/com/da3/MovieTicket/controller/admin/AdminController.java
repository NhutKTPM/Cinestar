package com.da3.MovieTicket.controller.admin;

import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.service.BillService;
import com.da3.MovieTicket.service.GiftCardService;
import com.da3.MovieTicket.service.RegionService;
import com.da3.MovieTicket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    private BillService billService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private GiftCardService giftCardService;

    @GetMapping("/admin")
    public String adminMainPage(Model model){
        Long totalEarnings = billService.getTotalAllTimeEarnings();
        model.addAttribute("totalEarnings", totalEarnings);

        Long ticketsSold = ticketService.countAllTickets();
        model.addAttribute("ticketsSold", ticketsSold);

        Long totalGiftCards = giftCardService.getTotalAllTimeGiftCardsSold();
        model.addAttribute("totalGiftCards", totalGiftCards);

        Long totalConcessions = billService.getTotalAllTimeConcessionsEarnings();
        model.addAttribute("totalConcessions", totalConcessions);


        // Get daily revenue
        List<Long> revenues = billService.getDailyRevenueForLastSevenDays();
        List<String> labels = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        for (int i = 6; i >= 0; i--) {
            LocalDateTime date = now.minusDays(i);
            labels.add(date.format(DateTimeFormatter.ofPattern("dd/MM")));
        }

        model.addAttribute("labels", labels);
        model.addAttribute("revenues", revenues);

        // Get revenue in the last seven days by movie
        Map<String, Long> movieRevenue = billService.getRevenueByMoviesLastSevenDays();
        model.addAttribute("movieTitles", new ArrayList<>(movieRevenue.keySet()));
        model.addAttribute("movieRevenues", new ArrayList<>(movieRevenue.values()));


        return "admin/admin-main";
    }

}
