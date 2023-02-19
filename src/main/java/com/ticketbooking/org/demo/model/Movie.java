package com.ticketbooking.org.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    //private double price;


    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<MovieTheaters> movieTheaters;

    @OneToMany(mappedBy = "fkMovie",cascade = CascadeType.ALL)
    private List<Show> shows;
}
