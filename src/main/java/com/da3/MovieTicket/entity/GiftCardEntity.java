package com.da3.MovieTicket.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "gift_card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long giftCardId;

    @Column(columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean enabled = true;

    private String giftCardCode;
    private Long initialBalance;
    private Long currentBalance;

    @ManyToOne
    @JoinColumn(name = "imageId")
    private GiftCardImageEntity giftCardImage;

    @ManyToOne
    @JoinColumn(name = "purchaserId")
    private UserEntity purchaser;  // User who bought the gift card

    @ManyToOne
    @JoinColumn(name = "recipientId")
    private UserEntity recipient;  // User who received the gift card (optional)

    @ManyToOne
    @JoinColumn(name = "usageId")
    private GiftCardUsageEntity giftCardUsages;  // User who received the gift card (optional)

    private LocalDateTime expirationDate;

}
