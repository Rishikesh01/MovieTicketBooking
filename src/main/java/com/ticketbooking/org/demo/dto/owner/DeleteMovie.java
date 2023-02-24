package com.ticketbooking.org.demo.dto.owner;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteMovie {
    @JsonProperty("id")
    private long id;
//    @JsonProperty("movie_id")
//    private int movieID;
//
//    @JsonProperty("theater_id")
//    private int theaterID;
}
