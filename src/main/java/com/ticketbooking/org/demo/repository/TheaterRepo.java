package com.ticketbooking.org.demo.repository;

import com.ticketbooking.org.demo.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheaterRepo extends JpaRepository<Theater,Integer> {
    List<Theater> findByNameContaining(String name);


}
