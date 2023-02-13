package com.ticketbooking.org.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/movie")
public class MovieBookingController {

    @PostMapping("/book")
    public ResponseEntity<HttpStatus> startBooking(){


        return ResponseEntity.accepted().build();

    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMovieToTheater(){


        return ResponseEntity.accepted().build();
    }




}
