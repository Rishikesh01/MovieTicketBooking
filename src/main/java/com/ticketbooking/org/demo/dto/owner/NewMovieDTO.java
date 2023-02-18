package com.ticketbooking.org.demo.dto.owner;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NewMovieDTO {

    @JsonProperty("fk_theater")
    private int fkTheater;

    @JsonProperty("fk_hall")
    private int fkHall;

    @JsonProperty("movie_name")
    private String movieName;

    private List<ShowsDTO> shows;
    private double price;
}
