package com.ticketbooking.org.demo.repository;

import com.ticketbooking.org.demo.model.Lock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LockRepo extends JpaRepository<Lock,Integer> {
    List<Lock> findByFkShowSeatIn(List<Integer> fkShowSeat);
}
