package com.da3.MovieTicket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    @Column(columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "showtimeId")
    private ShowtimeEntity showtime;

    @OneToMany(mappedBy = "bill")
    private List<TicketEntity> tickets;

    @OneToMany(mappedBy = "bill")
    private List<BillConcessionItemEntity> concessionItems;

    @OneToMany
    @JoinColumn(name = "bill_bill_id")
    private List<BillGiftCardUsageEntity> giftCardUsages;

    private String paymentMethod;

    private Integer rating;
    private String comment;

    public Long getTotalTicket(){
        Long total = 0L;
        // Add seats total
        for (TicketEntity ticket : tickets) {
            total = total + ticket.getSeat().getSeatType().getPrice();
        }
        return total;
    }

    public Long getTotalConcessions(){
        Long totalConcession = 0L;
        for (BillConcessionItemEntity item : concessionItems) {
            totalConcession = totalConcession + (item.getConcession().getPrice()
                    * (long) item.getQuantity());
        }
        return totalConcession;
    }

    public Long getTempTotal(){
        return getTotalTicket() + getTotalConcessions();
    }

    public Long getTotal() {
        Long total = getTempTotal();

        // Subtract gift cards
        for (BillGiftCardUsageEntity usage : giftCardUsages) {
            total = total - usage.getUsedAmount();
        }
        return total;
    }
}
