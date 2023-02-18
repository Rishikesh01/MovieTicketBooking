package com.ticketbooking.org.demo.dto.user;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShowsDTO {
    private int id;

    private String hallName;
    private int hallID;

    private LocalDateTime startTime;

}
