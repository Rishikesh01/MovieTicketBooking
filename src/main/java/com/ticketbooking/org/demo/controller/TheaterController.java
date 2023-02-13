package com.ticketbooking.org.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/theater")
public class TheaterController {

    @PostMapping
    public ResponseEntity<HttpStatus> addNewTheaterController(){


        return  ResponseEntity.accepted().build();
    }
}
