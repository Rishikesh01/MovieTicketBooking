package com.ticketbooking.org.demo.dto.owner;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CheckOutDTO {
    private String email;

    private ArrayList<Integer> fkSeats;
}