package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.BillConcessionItemEntity;
import com.da3.MovieTicket.repository.BillConcessionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillConcessionItemService {
    private final BillConcessionItemRepository billConcessionItemRepository;

    @Autowired
    public BillConcessionItemService(BillConcessionItemRepository billConcessionItemRepository){
        this.billConcessionItemRepository = billConcessionItemRepository;
    }

    public List<BillConcessionItemEntity> getAllBillConcessionItems (){
        return billConcessionItemRepository.findAll();
    }

    public void createBillConcessionItem(BillConcessionItemEntity billConcessionItem){
        billConcessionItemRepository.save(billConcessionItem);
    }

    public BillConcessionItemEntity getBillConcessionItemById(Long id) {
        return billConcessionItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid billConcessionItem Id: " + id));
    }
}
