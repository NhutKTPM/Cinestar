package com.da3.MovieTicket.repository;

import com.da3.MovieTicket.entity.Banner;
import com.da3.MovieTicket.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findAllByEnabledIsTrue();
}
