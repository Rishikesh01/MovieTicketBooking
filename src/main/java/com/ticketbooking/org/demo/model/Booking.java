package com.ticketbooking.org.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int numberOfSeats;


    @ManyToOne
    private User fkUser;

    @ManyToOne
    private Show fkShow;

    @OneToMany(mappedBy = "fkBookings")
    private List<ShowSeat> showSeats;
}
