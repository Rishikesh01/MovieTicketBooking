package com.ticketbooking.org.demo.repository;


import com.ticketbooking.org.demo.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowSeatsRepo extends JpaRepository<ShowSeat,Integer> {
    List<ShowSeat> findByFkShow(int fkshow);

}
