package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.ConcessionEntity;
import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.repository.ConcessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcessionService {
    private final ConcessionRepository concessionRepository;

    @Autowired
    public ConcessionService(ConcessionRepository concessionRepository){
        this.concessionRepository = concessionRepository;
    }

    public List<ConcessionEntity> getAllConcessions(){
        return concessionRepository.findAll();
    }

    public void createConcession(ConcessionEntity concession){
        concessionRepository.save(concession);
    }

    public ConcessionEntity getConcessionById(Long id) {
        return concessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid concession Id: " + id));
    }
    
}
