package com.ticketbooking.org.demo.repository;

import com.ticketbooking.org.demo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepo extends JpaRepository<Movie,Integer> {
   Optional< Movie> findByNameLike(String name);
}
