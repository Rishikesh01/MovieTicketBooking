package com.ticketbooking.org.demo.dto.user;


import lombok.Data;

import java.util.List;

@Data
public class TheatersDTO {
    private int id;

    private List<ShowsDTO> Shows;
}
