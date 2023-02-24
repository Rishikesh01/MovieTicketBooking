package com.ticketbooking.org.demo.dto.owner;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateSeatPrice {
    @JsonProperty("id")
   private long id;
   private double price;
}
