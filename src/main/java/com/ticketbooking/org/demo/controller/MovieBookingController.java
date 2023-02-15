package com.ticketbooking.org.demo.controller;


import com.ticketbooking.org.demo.dto.BookingDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/movie")
public class MovieBookingController {

    @PostMapping("/book")
    public ResponseEntity<HttpStatus> startBooking(@RequestBody BookingDTO bookingDTO){


        return ResponseEntity.accepted().build();

    }


}
