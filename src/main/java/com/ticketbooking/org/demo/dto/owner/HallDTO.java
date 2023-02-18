package com.ticketbooking.org.demo.dto.owner;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class HallDTO {
    private String name;

    @JsonProperty("total_seats")
    private int totalSeats;

    private int rows;

    private int columns;

    private List<SeatDTO> seats;
}
