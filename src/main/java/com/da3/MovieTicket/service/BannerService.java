package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.Banner;
import com.da3.MovieTicket.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {
    private final BannerRepository bannerRepository;

    @Autowired
    public BannerService(BannerRepository bannerRepository){
        this.bannerRepository = bannerRepository;
    }

    public List<Banner> getAllBanners (){
        return bannerRepository.findAll();
    }

    public List<Banner> getAllEnabledBanners(){
        return bannerRepository.findAllByEnabledIsTrue();
    }

    public void createBanner(Banner banner){
        bannerRepository.save(banner);
    }

    public Banner getBannerById(Long id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid banner Id: " + id));
    }

    public void disableBanner(Long id){
        Banner banner = getBannerById(id);
        if (banner.isEnabled()) {
            banner.setEnabled(false);
        } else {
            banner.setEnabled(true);
        }
        bannerRepository.save(banner);
    }
}
