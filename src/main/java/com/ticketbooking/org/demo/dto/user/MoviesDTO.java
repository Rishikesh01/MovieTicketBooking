package com.ticketbooking.org.demo.dto.user;


import lombok.Data;

import java.util.List;

@Data
public class MoviesDTO {
    private int id;

    private String name;


    private List<TheatersDTO> theater;
}
