package com.ticketbooking.org.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int fkHall;

    private int fkMovie;
    private LocalDate date;

    private LocalDateTime startTime;

    private LocalDate endTime;

    @OneToMany(mappedBy = "fkShow")
    private List<ShowSeat> showSeats;

    @OneToMany(mappedBy = "fkShow")
    private List<Booking> bookings;
}
