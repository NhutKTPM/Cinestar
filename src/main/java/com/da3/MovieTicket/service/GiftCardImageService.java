package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.GiftCardImageEntity;
import com.da3.MovieTicket.repository.GiftCardImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCardImageService {
    private final GiftCardImageRepository giftCardImageRepository;

    @Autowired
    public GiftCardImageService(GiftCardImageRepository giftCardImageRepository){
        this.giftCardImageRepository = giftCardImageRepository;
    }

    public List<GiftCardImageEntity> getAllGiftCardImages (){
        return giftCardImageRepository.findAll();
    }

    public void createGiftCardImage(GiftCardImageEntity giftCardImage){
        giftCardImageRepository.save(giftCardImage);
    }

    public GiftCardImageEntity getGiftCardImageById(Long id) {
        return giftCardImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid giftCardImage Id: " + id));
    }
}
