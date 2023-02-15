package com.ticketbooking.org.demo.service;


import com.ticketbooking.org.demo.dto.HallDTO;
import com.ticketbooking.org.demo.dto.NewMovieDTO;
import com.ticketbooking.org.demo.dto.SeatDTO;
import com.ticketbooking.org.demo.dto.TheaterDTO;
import com.ticketbooking.org.demo.model.*;
import com.ticketbooking.org.demo.repository.MovieRepo;
import com.ticketbooking.org.demo.repository.TheaterRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterService {
    private final MovieRepo movieRepo;
    private final TheaterRepo theaterRepo;

    public boolean check(TheaterDTO theaterDTO) {
        for (HallDTO hallDTO : theaterDTO.getHalls()) {
            if (!minSeatsCheck(hallDTO.getSeats())) {
                System.out.println(2);
                return false;
            }
        }
        return true;
    }

    public void addNewTheater(TheaterDTO theaterDTO) {
        Theater theater = new Theater();
        theater.setName(theaterDTO.getName());
        theater.setNumberOfHalls(theaterDTO.getNumberOfHalls());
        theater.setHalls(dtoToModel(theaterDTO.getHalls()));
        theater.getHalls().parallelStream().forEach(e -> e.setFkTheater(theater));
        theater.getHalls().parallelStream().forEach(e -> e.getSeats().parallelStream().forEach(
                m -> m.setFkHall(e)
        ));
        theaterRepo.save(theater);
    }

    public void addNewMovie(NewMovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setName(movieDTO.getMovieName());
        movie.setPrice(movieDTO.getPrice());
        movie.setShows(movieDTO.getShows().parallelStream().map(e -> {
            Show show = new Show();
            show.setDate(e.getDate());
            show.setFkMovie(e.getFkHall());
            show.setStartTime(e.getStartTime());
            show.setEndTime(e.getEndTime());
            return show;
        }).toList());
        movieRepo.save(movie);
    }

    private boolean minSeatsCheck(List<SeatDTO> seatDTOS) {
        for (SeatDTO seatDTO : seatDTOS) {
            if (seatDTO.getSeats().size() < 6) {
                return false;
            }
        }
        return true;
    }

    private List<Hall> dtoToModel(List<HallDTO> hallDTO) {
        List<Hall> halls = new ArrayList<>(hallDTO.size() + 1);
        for (HallDTO dto : hallDTO) {
            Hall hall = new Hall();
            hall.setName(dto.getName());
            hall.setRows(dto.getRows());
            hall.setColumns(dto.getColumns());
            List<Seat> seatList = new ArrayList<>();
            for (SeatDTO seat : dto.getSeats()) {
                for (String s : seat.getSeats()) {
                    Seat seats = new Seat();
                    seats.setRowNumber(seat.getRowNumber());
                    seats.setSeatNumber(s);
                    seatList.add(seats);
                }
            }
            hall.setSeats(seatList);
            halls.add(hall);
        }
        return halls;
    }


}
