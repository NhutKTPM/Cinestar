package com.da3.MovieTicket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "billConcessionItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillConcessionItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean enabled = true;

    @ManyToOne
    private ConcessionEntity concession;

    @ManyToOne
    @JoinColumn(name = "billId")
    private BillEntity bill;

    private int quantity;
}
