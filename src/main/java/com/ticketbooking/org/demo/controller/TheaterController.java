package com.ticketbooking.org.demo.controller;


import com.ticketbooking.org.demo.dto.GetTheater;
import com.ticketbooking.org.demo.dto.owner.NewMovieDTO;
import com.ticketbooking.org.demo.dto.owner.TheaterDTO;
import com.ticketbooking.org.demo.service.TheaterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/theater")
public class TheaterController {

    private final TheaterService theaterService;

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> addNewTheaterController(@RequestBody TheaterDTO dto) {
        if (!theaterService.check(dto)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        theaterService.addNewTheater(dto);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/add/movie")
    public ResponseEntity<HttpStatus> addNewMovieToTheater(@RequestBody NewMovieDTO movie) {
        try {
            theaterService.addNewMovie(movie);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/name/{nameOfTheater}")
    public ResponseEntity<List<GetTheater>> getMovie(@PathVariable("nameOfTheater") String  nameOfTheater){

        return ResponseEntity.ok(theaterService.getTheaters(nameOfTheater));
    }

}
