package com.ticketbooking.org.demo.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int fkTheater;


    private String name;

    private int totalSeats;

    private int rows;

    private int columns;

    @OneToMany(mappedBy = "fkHall")
    private List<Seat> seats;

    @OneToMany(mappedBy = "fkHall")
    private List<Show> shows;

}
