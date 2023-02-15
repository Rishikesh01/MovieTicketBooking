package com.ticketbooking.org.demo.service;


import com.ticketbooking.org.demo.dto.BookingDTO;
import com.ticketbooking.org.demo.repository.BookingRepo;
import com.ticketbooking.org.demo.repository.MovieRepo;
import com.ticketbooking.org.demo.repository.TheaterRepo;
import com.ticketbooking.org.demo.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookingService {
    private final UserRepo userRepo;
    private final TheaterRepo theaterRepo;

    private final MovieRepo movieRepo;
    private final BookingRepo bookingRepo;


    @Transactional
    public boolean bookSeat(BookingDTO bookingDTO){
       var user= userRepo.findByEmail(bookingDTO.getEmail());
       var theater=theaterRepo.findById(bookingDTO.getFkTheaterID());
        return true;
    }
}
