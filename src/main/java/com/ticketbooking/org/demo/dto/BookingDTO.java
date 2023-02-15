package com.ticketbooking.org.demo.dto;


import lombok.Data;

import java.util.ArrayList;

@Data
public class BookingDTO {

    private String theater;

    private int fkTheaterID;
    private int fkHall;
    private int movieID;

    private String movieName;

    private String email;

    private ArrayList<Integer> fkSeats;

    private int totalAmount;
}
