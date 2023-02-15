package com.ticketbooking.org.demo.dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ShowsDTO {
    private int fkHall;

    private LocalDate date;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
