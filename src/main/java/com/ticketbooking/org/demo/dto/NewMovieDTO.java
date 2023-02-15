package com.ticketbooking.org.demo.dto;


import lombok.Data;

import java.util.List;

@Data
public class NewMovieDTO {

    private String theaterName;

    private String hallName;

    private String movieName;

    private List<ShowsDTO> shows;
    private double price;
}
