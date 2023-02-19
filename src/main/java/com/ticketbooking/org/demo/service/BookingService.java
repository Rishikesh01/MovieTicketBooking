package com.ticketbooking.org.demo.service;


import com.ticketbooking.org.demo.dto.user.BookMovieSeatsDTO;
import com.ticketbooking.org.demo.dto.user.LockMovieSeatsDTO;
import com.ticketbooking.org.demo.model.Booking;
import com.ticketbooking.org.demo.model.ShowSeat;
import com.ticketbooking.org.demo.repository.BookingRepo;
import com.ticketbooking.org.demo.repository.ShowRepo;
import com.ticketbooking.org.demo.repository.ShowSeatsRepo;
import com.ticketbooking.org.demo.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final UserRepo userRepo;
    private final ShowRepo showRepo;
    private final BookingRepo bookingRepo;
    private final ShowSeatsRepo showSeatsRepo;
    public final Map<Integer, LockMovieSeatsDTO> map = Collections.synchronizedMap(new LinkedHashMap<>());

    @Transactional
    public boolean seatBooking(BookMovieSeatsDTO bookMovieSeatsDTO) throws Exception {
        var user = userRepo.findByEmail(bookMovieSeatsDTO.getEmail()).orElseThrow(() -> new Exception("user not found"));
        var showSeat = showSeatsRepo.findAllById(bookMovieSeatsDTO.getSeatIDs());
        if (!checkIfSeatsAreLockedOrBooked(showSeat, bookMovieSeatsDTO)) {
            return false;
        }
        List<Integer> seatIDs = showSeat.stream().map(ShowSeat::getId).toList();
        seatIDs.forEach(map::remove);
        showSeat.forEach(e -> e.setStatus(1));
        showSeatsRepo.saveAll(showSeat);
        Booking booking = new Booking();
        booking.setNumberOfSeats(showSeat.size());
        var show = showRepo.findById(bookMovieSeatsDTO.getShowID()).orElseThrow(() -> new Exception("illegal show key"));
        System.out.println(show.toString());
        booking.setFkShow(show);
        booking.setFkUser(user);
        bookingRepo.save(booking);
        return true;
    }

    private boolean checkIfSeatsAreLockedOrBooked(List<ShowSeat> showSeats, BookMovieSeatsDTO bookMovieSeatsDTO) {
        for (ShowSeat seat : showSeats) {
            if (seat.getStatus() == 1 || (map.get(seat.getId()).getExp().isAfter(Instant.now()) && !map.get(seat.getId()).getEmail().equals(bookMovieSeatsDTO.getEmail()))) {
                return true;
            }
        }
        return false;
    }

    public boolean lockMovieSeats(LockMovieSeatsDTO lockMovieSeatsDTO) throws Exception {
        userRepo.findByEmail(lockMovieSeatsDTO.getEmail()).orElseThrow(() -> new Exception("user not found"));
        var nonLockList = lockMovieSeatsDTO.getSeatIDs().stream().filter(e -> !map.containsKey(e) || !map.get(e).getExp().isAfter(Instant.now())).toList();
        if (nonLockList.size() != lockMovieSeatsDTO.getSeatIDs().size()) {
            return false;
        }
        nonLockList.forEach(e -> {
            lockMovieSeatsDTO.setExp(Instant.now().plus(10, ChronoUnit.MINUTES));
            map.put(e, lockMovieSeatsDTO);
        });
        return true;
    }

}
