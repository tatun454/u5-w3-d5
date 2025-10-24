package com.jason.u5_w3_d5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime date;
    private String location;
    private int totalSeats;
    private int availableSeats;


    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;


    @OneToMany(mappedBy = "event")
    private List<Booking> bookings;
}