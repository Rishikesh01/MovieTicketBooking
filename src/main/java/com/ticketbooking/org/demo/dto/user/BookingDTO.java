package com.ticketbooking.org.demo.dto.user;


import lombok.Data;

import java.util.ArrayList;

@Data
public class BookingDTO {

    private int fkHall;

    private int fkShowID;
    private int fkMovieID;

    private String email;

    private ArrayList<Integer> fkSeats;

}
