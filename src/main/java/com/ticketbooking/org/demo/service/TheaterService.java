package com.ticketbooking.org.demo.service;


import com.ticketbooking.org.demo.dto.GetTheater;
import com.ticketbooking.org.demo.dto.owner.HallDTO;
import com.ticketbooking.org.demo.dto.owner.NewMovieDTO;
import com.ticketbooking.org.demo.dto.owner.SeatDTO;
import com.ticketbooking.org.demo.dto.owner.TheaterDTO;
import com.ticketbooking.org.demo.model.*;
import com.ticketbooking.org.demo.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final MovieTheaterRepo movieTheaterRepo;
    private final ShowSeatsRepo showSeatsRepo;
    private final HallRepo hallRepo;
    private final MovieRepo movieRepo;
    private final TheaterRepo theaterRepo;

    private final ShowRepo showRepo;

    public boolean check(TheaterDTO theaterDTO) {
        for (HallDTO hallDTO : theaterDTO.getHalls()) {
            if (!minSeatsCheck(hallDTO.getSeats())) {
                System.out.println(2);
                return false;
            }
        }
        return true;
    }

    public List<GetTheater> getTheaters(String name) {
        var theater = theaterRepo.findByNameContaining(name);

        return theater.stream().map(e->{
            var getTheater = new GetTheater();
            getTheater.setId(e.getId());
            getTheater.setName(e.getName());
            Map<Integer,String> map = new HashMap<>();
            for(MovieTheaters movieTheaters:e.getMovies()){
                       map.put(movieTheaters.getMovie().getId(),movieTheaters.getMovie().getName());
            }
            getTheater.setMovies(map);
            return getTheater;
        }).toList();

        /*theater.stream().map(e -> {
            var getTheater = new GetTheater();
            getTheater.setId(e.getId());
            getTheater.setName(e.getName());
            getTheater.setMovies(e.getMovies().stream().collect(Collectors.toMap(Movie::getId, Movie::getName)));
            return getTheater;
        }).toList();*/
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



    public void addNewMovie(NewMovieDTO movieDTO) throws Exception {
        var hall = hallRepo.findById(movieDTO.getFkHall()).orElseThrow(()->new Exception("illegal hall key"));
        var mv = movieRepo.findByName(movieDTO.getMovieName());
        if (mv.isPresent()){
            update(mv.get(),movieDTO,hall);
            return;
        }
       addMovie(movieDTO,hall);

    }

    @Transactional
    private void update(Movie movie,NewMovieDTO movieDTO,Hall hall){
        var list = movieDTO.getShows().parallelStream().map(e -> {
            Show show = new Show();
            show.setDate(e.getDate());
            show.setFkHall(hall);
            show.setStartTime(e.getStartTime());
            show.setEndTime(e.getEndTime());
            return show;
        }).toList();
        movie.setShows(list);
        movie.setName(movieDTO.getMovieName());
        var list1 = list.stream().map(
                e-> hall.getSeats().stream().map(
                        m->{
                            var showSeats = new ShowSeat();
                            showSeats.setFkSeats(m);
                            showSeats.setFkShow(e);
                            return showSeats;
                        }
                ).toList()
        ).toList();
        movie.setShows(movie.getShows());
        movie.getShows().parallelStream().forEach(e->e.setFkMovie(movie));
        showRepo.saveAll(list);
        showSeatsRepo.saveAll(list1.parallelStream().flatMap(List::stream).toList());

        movieTheaterRepo.save(new MovieTheaters(movie,new Theater(movieDTO.getFkTheater(),null,0,null,null),movieDTO.getPrice(),0L));
    }

   /* @Transactional
    private void update(Movie movie,NewMovieDTO movieDTO,Hall hall){
        var theater = theaterRepo.findById(movieDTO.getFkTheater()).get();

        var list = movieDTO.getShows().parallelStream().map(e -> {
            Show show = new Show();
            show.setDate(e.getDate());
            show.setFkHall(hall);
            show.setStartTime(e.getStartTime());
            show.setEndTime(e.getEndTime());
            return show;
        }).toList();

        movie.setFkTheater(List.of(theater));
        movie.setShows(list);
        movie.setShows(movie.getShows());
        movie.getShows().parallelStream().forEach(e->e.setFkMovie(movie));


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

        movieRepo.save(movie);
        showSeatsRepo.saveAll(list1.parallelStream().flatMap(List::stream).toList());
    }*/


    @Transactional
    private void addMovie(NewMovieDTO movieDTO,Hall hall){
        Movie movie = new Movie();

        var list = movieDTO.getShows().parallelStream().map(e -> {
            Show show = new Show();
            show.setDate(e.getDate());
            show.setFkHall(hall);
            show.setFkMovie(movie);
            show.setStartTime(e.getStartTime());
            show.setEndTime(e.getEndTime());
            return show;
        }).toList();

        movie.setShows(list);
        movie.setName(movieDTO.getMovieName());

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
        movie.setShows(movie.getShows());
        movie.getShows().parallelStream().forEach(e->e.setFkMovie(movie));

        movieRepo.save(movie);
        showSeatsRepo.saveAll(list1.parallelStream().flatMap(List::stream).toList());

        movieTheaterRepo.save(new MovieTheaters(movie,new Theater(movieDTO.getFkTheater(),null,0,null,null),movieDTO.getPrice(),0L));

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
