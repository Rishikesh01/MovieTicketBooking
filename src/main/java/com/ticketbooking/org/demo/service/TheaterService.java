package com.ticketbooking.org.demo.service;


import com.ticketbooking.org.demo.dto.owner.HallDTO;
import com.ticketbooking.org.demo.dto.owner.NewMovieDTO;
import com.ticketbooking.org.demo.dto.owner.SeatDTO;
import com.ticketbooking.org.demo.dto.owner.TheaterDTO;
import com.ticketbooking.org.demo.model.*;
import com.ticketbooking.org.demo.repository.HallRepo;
import com.ticketbooking.org.demo.repository.MovieRepo;
import com.ticketbooking.org.demo.repository.ShowSeatsRepo;
import com.ticketbooking.org.demo.repository.TheaterRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final ShowSeatsRepo showSeatsRepo;
    private final HallRepo hallRepo;
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

    @Transactional
    public void addNewMovie(NewMovieDTO movieDTO) throws Exception {
        var hall = hallRepo.findById(movieDTO.getFkHall()).orElseThrow(()->new Exception("illegal hall key"));
        Movie movie = new Movie();
        movie.setName(movieDTO.getMovieName());
        movie.setPrice(movieDTO.getPrice());
        var list = movieDTO.getShows().parallelStream().map(e -> {
            Show show = new Show();
            show.setDate(e.getDate());
            show.setFkHall(hall);
            show.setStartTime(e.getStartTime());
            show.setEndTime(e.getEndTime());
            return show;
        }).toList();
        List<Theater> theaters = new ArrayList<>();
        theaters.add(new Theater(movieDTO.getFkTheater(), null,0,null,null));
        movie.setFkTheater(theaters);
        movie.setShows(list);
        movie.getShows().parallelStream().forEach(e->e.setFkMovie(movie));
        movieRepo.save(movie);

        var list1 = movie.getShows().stream().map(
                e-> hall.getSeats().stream().map(
                         m->{
                             var showSeats = new ShowSeat();
                             showSeats.setFkSeats(m);
                             showSeats.setFkShow(e);
                             return showSeats;
                         }
                 ).toList()
        ).toList();
        showSeatsRepo.saveAll(list1.parallelStream().flatMap(List::stream).toList());

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
            hall.setTotalSeats(dto.getTotalSeats());
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
