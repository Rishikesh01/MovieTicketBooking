package com.ticketbooking.org.demo.dto.owner;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SeatDTO {

    @JsonProperty("row_number")
    private int rowNumber;
    private List<String> seats;
}
