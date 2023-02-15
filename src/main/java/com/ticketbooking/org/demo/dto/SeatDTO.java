package com.ticketbooking.org.demo.dto;


import lombok.Data;

import java.util.List;

@Data
public class SeatDTO {
    private int rowNumber;
    private List<String> seats;
}
