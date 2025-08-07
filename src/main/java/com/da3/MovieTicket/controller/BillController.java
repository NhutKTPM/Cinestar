package com.da3.MovieTicket.controller;

import com.da3.MovieTicket.dto.CartDTO;
import com.da3.MovieTicket.entity.BillEntity;
import com.da3.MovieTicket.mapper.CartMapper;
import com.da3.MovieTicket.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BillController {
    private final BillService billService;
    private final CartMapper cartMapper;

    @Autowired
    public BillController(BillService billService,
                          CartMapper cartMapper){
        this.billService = billService;
        this.cartMapper = cartMapper;
    }

    @GetMapping("/bill/{id}")
    public String getBill(@PathVariable("id") long billId, Model model){
        BillEntity bill = billService.getBillById(billId);
        model.addAttribute("cart", cartMapper.toDto(bill));
        model.addAttribute("bill", bill);
        return "bill/bill-detail";
    }

    @PostMapping("/bill/{id}/postRating")
    public String postRating(@PathVariable("id") long billId, BillEntity bill){
        BillEntity billEntity = billService.getBillById(billId);
        billEntity.setRating(bill.getRating());
        billEntity.setComment(bill.getComment());
        billService.updateBill(billEntity);
        return "redirect:/bill/" + billId;
    }


}
