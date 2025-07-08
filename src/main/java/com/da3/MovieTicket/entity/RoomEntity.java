package com.da3.MovieTicket.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean enabled = true;

    private String roomName;

    @ManyToOne
    @JoinColumn(name = "cinemaId", nullable = false)
    private CinemaEntity cinema;

    @ManyToOne
    @JoinColumn(name = "roomTypeId", columnDefinition = "bigint default 1")
    private RoomTypeEntity roomType;

//        @JoinColumn(name = "regionTypeId", columnDefinition = "bigint default 1")
}
