package com.ticketbooking.org.demo.repository;

import com.ticketbooking.org.demo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie,Integer> {
}
