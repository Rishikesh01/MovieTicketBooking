package com.ticketbooking.org.demo.service;


import com.ticketbooking.org.demo.dto.user.BookingDTO;
import com.ticketbooking.org.demo.dto.owner.CheckOutDTO;
import com.ticketbooking.org.demo.model.Booking;
import com.ticketbooking.org.demo.model.Lock;
import com.ticketbooking.org.demo.model.ShowSeat;
import com.ticketbooking.org.demo.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final LockRepo lockRepo;
    private final UserRepo userRepo;

    private final MovieRepo movieRepo;
    private final BookingRepo bookingRepo;

    private final ShowSeatsRepo showSeatsRepo;


    @Transactional
    public boolean bookSeat(BookingDTO bookingDTO) throws Exception {
       var user= userRepo.findByEmail(bookingDTO.getEmail()).orElseThrow(()-> new Exception("user not found"));
       var movie = movieRepo.findById(bookingDTO.getFkMovieID());
      var showSeat = showSeatsRepo.findAllById(bookingDTO.getFkSeats());
        for (ShowSeat seat : showSeat) {
            if (seat.getStatus() == 1||seat.getLock().size()>0) {
                return false;
            }
        }
        showSeat.parallelStream().forEach(e->e.setStatus(1));
        showSeatsRepo.saveAll(showSeat);
        Booking booking = new Booking();
        booking.setNumberOfSeats(showSeat.size());
        booking.setFkShow(bookingDTO.getFkShowID());
        booking.setFkUser(user.getId());
       bookingRepo.save(booking);
       return true;
    }

    public boolean bookingTempLock(CheckOutDTO checkOutDTO) throws Exception {
        var lock= lockRepo.findByFkShowSeatIn(checkOutDTO.getFkSeats());
        if(lock.size()!=0){
            return false;
        }
        var user = userRepo.findByEmail(checkOutDTO.getEmail()).orElseThrow(()->new Exception("user not found"));

        lockRepo.saveAll(checkOutDTO.
                getFkSeats().
                parallelStream().
                map(e-> new Lock(0,user.getId(),e, Instant.now())).
                toList());

        return true;
    }

}
