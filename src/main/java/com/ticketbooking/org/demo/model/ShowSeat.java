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

    private int fkShow;

    private int fkSeats;

    private int fkBookings;

    @OneToMany(mappedBy = "fkShowSeat")
    private List<Lock> lock;

}
