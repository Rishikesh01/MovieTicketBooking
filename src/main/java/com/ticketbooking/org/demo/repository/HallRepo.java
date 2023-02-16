package com.ticketbooking.org.demo.repository;

import com.ticketbooking.org.demo.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepo extends JpaRepository<Hall,Integer> {
}
