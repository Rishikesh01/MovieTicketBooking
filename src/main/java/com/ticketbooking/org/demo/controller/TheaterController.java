package com.ticketbooking.org.demo.controller;


import com.ticketbooking.org.demo.dto.NewMovieDTO;
import com.ticketbooking.org.demo.dto.TheaterDTO;
import com.ticketbooking.org.demo.service.TheaterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/theater")
public class TheaterController {

    private final TheaterService theaterService;
    @PostMapping("/new")
    public ResponseEntity<HttpStatus> addNewTheaterController(@RequestBody TheaterDTO dto){
        if(!theaterService.check(dto)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        theaterService.addNewTheater(dto);
        return  ResponseEntity.accepted().build();
    }

    @PostMapping("/add/movie")
    public ResponseEntity<HttpStatus> addNewMovieToTheater(@RequestBody NewMovieDTO movie){
        try {
            theaterService.addNewMovie(movie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.accepted().build();
    }
}
