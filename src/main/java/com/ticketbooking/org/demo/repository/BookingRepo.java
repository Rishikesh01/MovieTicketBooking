package com.ticketbooking.org.demo.repository;

import com.ticketbooking.org.demo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking,Integer> {
}
