package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.RegionEntity;
import com.da3.MovieTicket.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {
    private final RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository){
        this.regionRepository = regionRepository;
    }

    public List<RegionEntity> getAllRegions (){
        return regionRepository.findAll();
    }

    public void createRegion(RegionEntity region){
        regionRepository.save(region);
    }

    public RegionEntity getRegionById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid region Id: " + id));
    }
}
