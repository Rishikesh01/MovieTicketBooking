package com.ticketbooking.org.demo.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_theater")
    private Theater fkTheater;


    private String name;

    private int totalSeats;

    private int rows;

    private int columns;

    @OneToMany(mappedBy = "fkHall",cascade = CascadeType.ALL)
    private List<Seat> seats;

    @OneToMany(mappedBy = "fkHall",cascade = CascadeType.ALL)
    private List<Show> shows;

}
