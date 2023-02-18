package com.ticketbooking.org.demo.repository;

import com.ticketbooking.org.demo.model.Movie;
import com.ticketbooking.org.demo.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepo extends JpaRepository<Show,Integer> {
    Show findAllByFkMovie(Movie fkMovie);
}
