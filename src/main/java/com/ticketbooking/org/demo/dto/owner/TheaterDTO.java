package com.ticketbooking.org.demo.dto.owner;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TheaterDTO {
    private String name;
    @JsonProperty("number_of_halls")
    private int numberOfHalls;

    private List<HallDTO> halls;

}
