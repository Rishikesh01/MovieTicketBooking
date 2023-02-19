package com.ticketbooking.org.demo.dto;

import lombok.Data;

import java.util.Map;


@Data
public class GetTheater {
    private int id;

    private String Name;

    private Map<Integer,String> movies;
}
