package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.BillGiftCardUsageEntity;
import com.da3.MovieTicket.repository.BillGiftCardUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillGiftCardUsageService {
    private final BillGiftCardUsageRepository billGiftCardUsageRepository;

    @Autowired
    public BillGiftCardUsageService(BillGiftCardUsageRepository billGiftCardUsageRepository){
        this.billGiftCardUsageRepository = billGiftCardUsageRepository;
    }

    public List<BillGiftCardUsageEntity> getAllBillGiftCardUsages (){
        return billGiftCardUsageRepository.findAll();
    }

    public void createBillGiftCardUsage(BillGiftCardUsageEntity billGiftCardUsage){
        billGiftCardUsageRepository.save(billGiftCardUsage);
    }

    public BillGiftCardUsageEntity getBillGiftCardUsageById(Long id) {
        return billGiftCardUsageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid billGiftCardUsage Id: " + id));
    }
}
