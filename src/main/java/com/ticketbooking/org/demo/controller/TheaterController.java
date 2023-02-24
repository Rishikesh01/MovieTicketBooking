package com.ticketbooking.org.demo.controller;


import com.ticketbooking.org.demo.dto.GetTheater;
import com.ticketbooking.org.demo.dto.owner.DeleteMovie;
import com.ticketbooking.org.demo.dto.owner.NewMovieDTO;
import com.ticketbooking.org.demo.dto.owner.NewTheaterDTO;
import com.ticketbooking.org.demo.dto.owner.UpdateSeatPrice;
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
    public ResponseEntity<HttpStatus> addNewTheaterController(@RequestBody NewTheaterDTO dto) {
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetTheater>> getAllTheaters(){
        return ResponseEntity.ok(theaterService.getAllTheater());
    }

    @GetMapping("/name/{nameOfTheater}")
    public ResponseEntity<List<GetTheater>> getMovieInTheater(@PathVariable("nameOfTheater") String  nameOfTheater){
        return ResponseEntity.ok(theaterService.getTheaterWithName(nameOfTheater));
    }


    @PatchMapping("/update/movie/price")
    public ResponseEntity<HttpStatus> updateMoviePrice(@RequestBody UpdateSeatPrice updateSeatPrice){
        if(theaterService.updateMoviePrice(updateSeatPrice))
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteMovieFromTheater(@RequestBody DeleteMovie deleteMovie){
        theaterService.deleteMovieFromTheater(deleteMovie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
