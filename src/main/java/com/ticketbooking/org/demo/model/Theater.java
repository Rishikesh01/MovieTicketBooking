package com.ticketbooking.org.demo.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int numberOfHalls;


    @OneToMany(mappedBy = "fkTheater",cascade = CascadeType.ALL)
    private List<Hall> halls = new ArrayList<>();

    @OneToMany(mappedBy = "theater",cascade = CascadeType.ALL)
    private List<MovieTheaters> movies= new ArrayList<>();

}
