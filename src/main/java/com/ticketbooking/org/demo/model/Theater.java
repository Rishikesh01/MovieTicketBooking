package com.ticketbooking.org.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int numberOfHalls;


    @OneToMany(mappedBy = "fkTheater",cascade = CascadeType.ALL)
    private List<Hall> halls;

    @OneToMany(mappedBy = "fkTheater",cascade = CascadeType.ALL)
    private List<Movie> movies;

}
