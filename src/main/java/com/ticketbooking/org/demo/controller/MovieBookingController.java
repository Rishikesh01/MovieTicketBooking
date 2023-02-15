package com.ticketbooking.org.demo.controller;


import com.ticketbooking.org.demo.dto.BookingDTO;
import com.ticketbooking.org.demo.dto.CheckOutDTO;
import com.ticketbooking.org.demo.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/v1/movie")
public class MovieBookingController {

    private final BookingService bookingService;
    @PostMapping("/temp/book")
    public  ResponseEntity<HttpStatus> tempLock(@RequestBody CheckOutDTO checkOutDTO){
        try {
            bookingService.bookingTempLock(checkOutDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.accepted().build();
    }
    @PostMapping("/book")
    public ResponseEntity<HttpStatus> startBooking(@RequestBody BookingDTO bookingDTO){
        try {
            bookingService.bookSeat(bookingDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.accepted().build();

    }


}
