package com.ticketbooking.org.demo.controller;


import com.ticketbooking.org.demo.dto.user.BookMovieSeatsDTO;
import com.ticketbooking.org.demo.dto.user.LockMovieSeatsDTO;
import com.ticketbooking.org.demo.dto.user.MovieHallSeatsDTO;
import com.ticketbooking.org.demo.dto.user.MoviesDTO;
import com.ticketbooking.org.demo.service.BookingService;
import com.ticketbooking.org.demo.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/movie")
public class MovieBookingController {

    private final MovieService movieService;
    private final BookingService bookingService;
    @PostMapping("/temp/book")
    public  ResponseEntity<HttpStatus> tempBookSeats(@RequestBody LockMovieSeatsDTO lockMovieSeatsDTO){
        try {
           if( bookingService.lockMovieSeats(lockMovieSeatsDTO)){
                return ResponseEntity.status(HttpStatus.OK).build();
           }
           return ResponseEntity.status(HttpStatus.LOCKED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
    @PostMapping("/book")
    public ResponseEntity<HttpStatus> bookSeats(@RequestBody BookMovieSeatsDTO bookMovieSeatsDTO){
        try {
            if(bookingService.seatBooking(bookMovieSeatsDTO)){
                return ResponseEntity.ok(HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping("/{movieName}")
    public ResponseEntity<MoviesDTO> getAllMovies(@PathVariable("movieName") String movieName)  {
        MoviesDTO movies = null;
        try {
            movies = movieService.getListOfTheater(movieName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/seats/{showID}")
    public ResponseEntity<List<MovieHallSeatsDTO>> getShowSeats(@PathVariable("showID")int showID){
       var seats =  movieService.getShowSeats(showID);
        return seats.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }


}
