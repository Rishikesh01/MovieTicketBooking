package com.ticketbooking.org.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MovieTheaters {
    @ManyToOne
    @JoinColumn(name = "fk_movie")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "fk_theater")
    private Theater theater;

    private double price;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
