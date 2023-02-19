package com.ticketbooking.org.demo.repository;

import com.ticketbooking.org.demo.model.Movie;
import com.ticketbooking.org.demo.model.MovieTheaters;
import com.ticketbooking.org.demo.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieTheaterRepo extends JpaRepository<MovieTheaters,Long> {
    List<MovieTheaters> findByMovie(Movie movie);

    List<MovieTheaters> findByTheater(Theater theater);
}
