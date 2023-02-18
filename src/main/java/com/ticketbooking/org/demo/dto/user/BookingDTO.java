package com.ticketbooking.org.demo.dto.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookingDTO {
    private String email;

    @JsonProperty("theater_id")
    private int theaterID;

    @JsonProperty("hall_id")
    private int hallID;



    @JsonProperty("show_id")
    private int showID;

    @JsonProperty("seat_ids")
    private List<Integer> seatIDs;

}
