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
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "integer default '0'")
    private int status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_show")
    private Show fkShow;


    @ManyToOne
    @JoinColumn(name = "fk_seats")
    private Seat fkSeats;

    @ManyToOne
    @JoinColumn(name = "fk_booking")
    private Booking fkBookings;



}
