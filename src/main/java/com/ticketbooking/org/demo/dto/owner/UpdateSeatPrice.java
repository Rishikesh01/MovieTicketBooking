package com.ticketbooking.org.demo.dto.owner;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateSeatPrice {
    @JsonProperty("theater_id")
   private int theaterID;
   private double price;
}
