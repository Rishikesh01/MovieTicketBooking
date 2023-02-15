package com.ticketbooking.org.demo.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Seat {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_hall")
    private Hall fkHall;

    private int rowNumber;

    private String seatNumber;

    @OneToMany(mappedBy = "fkSeats",cascade = CascadeType.ALL)
    private List<ShowSeat> showSeats;
}
