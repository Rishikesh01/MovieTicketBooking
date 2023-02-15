package com.ticketbooking.org.demo.dto;


import lombok.Data;

import java.util.List;

@Data
public class HallDTO {
    private String name;
    private int totalSeats;

    private int rows;

    private int columns;

    private List<SeatDTO> seatDTO;
}
