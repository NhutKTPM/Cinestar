package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.BillEntity;
import com.da3.MovieTicket.entity.ShowtimeEntity;
import com.da3.MovieTicket.entity.UserEntity;
import com.da3.MovieTicket.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

    public Long getTotalAllTimeEarnings() {
        List<BillEntity> allBills = getAllBills();
        return allBills.stream()
                .filter(BillEntity::isEnabled)  // Only count enabled bills
                .mapToLong(BillEntity::getTempTotal)
                .sum();
    }

    public Long getTotalAllTimeConcessionsEarnings() {
        List<BillEntity> allBills = getAllBills();
        return allBills.stream()
                .filter(BillEntity::isEnabled)  // Only count enabled bills
                .mapToLong(BillEntity::getTotalConcessions)
                .sum();
    }



    public List<Long> getDailyRevenueForLastSevenDays() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = now.minusDays(6).withHour(0).withMinute(0).withSecond(0).withNano(0);

        List<BillEntity> bills = billRepository.findByCreatedAtGreaterThanEqualAndEnabledOrderByCreatedAtAsc(sevenDaysAgo, true);

        // Initialize revenue for each day
        List<Long> dailyRevenue = new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L, 0L, 0L, 0L));

        for (BillEntity bill : bills) {
            LocalDateTime billDate = bill.getCreatedAt();
            int dayIndex = (int) ChronoUnit.DAYS.between(sevenDaysAgo.toLocalDate(), billDate.toLocalDate());
            if (dayIndex >= 0 && dayIndex < 7) {
                dailyRevenue.set(dayIndex, dailyRevenue.get(dayIndex) + bill.getTempTotal());
            }
        }

        return dailyRevenue;
    }



    public Map<String, Long> getRevenueByMoviesLastSevenDays() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = now.minusDays(6).withHour(0).withMinute(0).withSecond(0).withNano(0);

        List<BillEntity> bills = billRepository.findByCreatedAtGreaterThanEqualAndEnabledOrderByCreatedAtAsc(sevenDaysAgo, true);
        Map<String, Long> movieRevenue = new HashMap<>();

        for (BillEntity bill : bills) {
            if (bill.getShowtime() != null && bill.getShowtime().getMovie() != null) {
                String movieTitle = bill.getShowtime().getMovie().getMovieName();
                Long revenue = bill.getTempTotal();
                movieRevenue.merge(movieTitle, revenue, Long::sum);
            }
        }

        return movieRevenue;
    }
}
