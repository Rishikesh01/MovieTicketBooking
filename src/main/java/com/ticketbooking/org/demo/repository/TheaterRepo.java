package com.ticketbooking.org.demo.repository;

import com.ticketbooking.org.demo.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepo extends JpaRepository<Theater,Integer> {



}
