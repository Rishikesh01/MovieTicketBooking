package com.ticketbooking.org.demo.dto;


import lombok.Data;

import java.util.List;

@Data
public class TheaterDTO {
    private String name;
    private int numberOfHalls;

    private List<HallDTO> halls;

}
