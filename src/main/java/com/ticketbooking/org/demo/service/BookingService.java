package com.ticketbooking.org.demo.service;


import com.ticketbooking.org.demo.dto.user.BookingDTO;
import com.ticketbooking.org.demo.dto.user.LockMovieDTO;
import com.ticketbooking.org.demo.model.Booking;
import com.ticketbooking.org.demo.model.Show;
import com.ticketbooking.org.demo.model.ShowSeat;
import com.ticketbooking.org.demo.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final UserRepo userRepo;

    private final  ShowRepo showRepo;
    private final BookingRepo bookingRepo;

    private final ShowSeatsRepo showSeatsRepo;

    public final Map<Integer, LockMovieDTO> map = Collections.synchronizedMap(new LinkedHashMap<>());


    @Transactional
    public boolean bookSeat(BookingDTO bookingDTO) throws Exception {
        var user = userRepo.findByEmail(bookingDTO.getEmail()).orElseThrow(() -> new Exception("user not found"));
        var showSeat = showSeatsRepo.findAllById(bookingDTO.getSeatIDs());
        for (ShowSeat seat : showSeat) {
            if (seat.getStatus() == 1 || (map.get(seat.getId()).getExp().isAfter(Instant.now()) && !map.get(seat.getId()).getEmail().equals(bookingDTO.getEmail()))) {
                return false;
            }
        }
        showSeat.parallelStream().forEach(e -> e.setStatus(1));
        showSeatsRepo.saveAll(showSeat);
        Booking booking = new Booking();
        booking.setNumberOfSeats(showSeat.size());
        var show = showRepo.findById(bookingDTO.getShowID()).orElseThrow(()->new Exception("illegal show key"));
        System.out.println(show.toString());
        booking.setFkShow(show);
        booking.setFkUser(user);
        bookingRepo.save(booking);
        return true;
    }

    public boolean bookingTempLock(LockMovieDTO lockMovieDTO) throws Exception {
        var user = userRepo.findByEmail(lockMovieDTO.getEmail()).orElseThrow(() -> new Exception("user not found"));
        var nonLockList = lockMovieDTO.getSeatIDs().parallelStream().filter(e -> !map.containsKey(e) || !map.get(e).getExp().isAfter(Instant.now())).toList();
        if (nonLockList.size() != lockMovieDTO.getSeatIDs().size()) {
            return false;
        }
        nonLockList.parallelStream().forEach(e -> {
            lockMovieDTO.setExp(Instant.now().plus(10, ChronoUnit.MINUTES));
            map.put(e, lockMovieDTO);
        });
        return true;
    }

}
