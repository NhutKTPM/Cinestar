package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.BillEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import com.da3.MovieTicket.entity.UserEntity;
import com.da3.MovieTicket.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {
    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository){
        this.billRepository = billRepository;
    }

    public List<BillEntity> getAllBills (){
        return billRepository.findAll();
    }

    public BillEntity createBill(BillEntity bill){
        return billRepository.save(bill);
    }

    public BillEntity getBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bill Id: " + id));
    }

    public List<BillEntity> getBillsByUser(UserEntity user){
        return billRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    public List<BillEntity> getBillsByShowtime(ShowtimeEntity showtime){
        return billRepository.findAllByShowtime(showtime);
    }
}
