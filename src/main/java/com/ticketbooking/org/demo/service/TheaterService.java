package com.ticketbooking.org.demo.service;


import com.ticketbooking.org.demo.dto.GetTheater;
import com.ticketbooking.org.demo.dto.owner.*;
import com.ticketbooking.org.demo.model.*;
import com.ticketbooking.org.demo.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final MovieTheaterRepo movieTheaterRepo;
    private final ShowSeatsRepo showSeatsRepo;
    private final HallRepo hallRepo;
    private final MovieRepo movieRepo;
    private final TheaterRepo theaterRepo;

    private final ShowRepo showRepo;

    public boolean check(NewTheaterDTO newTheaterDTO) {
        for (NewHallDTO newHallDTO : newTheaterDTO.getHalls()) {
            if (!minSeatsCheck(newHallDTO.getSeats())) {
                System.out.println(2);
                return false;
            }
        }
        return true;
    }

    public List<GetTheater> getAllTheater(){
        var theater = theaterRepo.findAll();

        return toGetTheaterList(theater);
    }

    @NotNull
    private List<GetTheater> toGetTheaterList(List<Theater> theater) {
        return theater.stream().map(e -> {
            var getTheater = new GetTheater();
            getTheater.setId(e.getId());
            getTheater.setName(e.getName());
            Map<Integer, String> map = new HashMap<>();
            for (MovieTheaters movieTheaters : e.getMovies()) {
                map.put(movieTheaters.getMovie().getId(), movieTheaters.getMovie().getName());
            }
            getTheater.setMovies(map);
            return getTheater;
        }).toList();
    }

    public List<GetTheater> getTheaterWithName(String name) {
        var theater = theaterRepo.findByNameContaining(name);

        return toGetTheaterList(theater);
    }

    public void addNewTheater(NewTheaterDTO newTheaterDTO) {
        Theater theater = new Theater();
        theater.setName(newTheaterDTO.getName());
        theater.setNumberOfHalls(newTheaterDTO.getNumberOfHalls());
        theater.setHalls(toHallList(newTheaterDTO.getHalls()));
        theater.getHalls().forEach(e -> e.setFkTheater(theater));
        theater.getHalls().forEach(e -> e.getSeats().forEach(m -> m.setFkHall(e)));
        theaterRepo.save(theater);
    }

    public void deleteMovieFromTheater(DeleteMovie deleteMovie){
        movieTheaterRepo.deleteById(deleteMovie.getId());
    }

    public void addNewMovie(NewMovieDTO movieDTO) throws Exception {
        var hall = hallRepo.findById(movieDTO.getFkHall()).orElseThrow(() -> new Exception("illegal hall key"));
        var mv = movieRepo.findByName(movieDTO.getMovieName());
        if (mv.isPresent()) {
            update(mv.get(), movieDTO, hall);
            return;
        }
        addMovie(movieDTO, hall);

    }

    @Transactional
    private void update(Movie movie, NewMovieDTO movieDTO, Hall hall) {
        var shows = toShowList(movieDTO.getShows(), null, hall);
        movie.setShows(shows);
        movie.setName(movieDTO.getMovieName());
        var showSeats = toShowSeatsList(movie.getShows(), hall.getSeats());
        movie.setShows(movie.getShows());
        movie.getShows().forEach(e -> e.setFkMovie(movie));
        showRepo.saveAll(shows);
        showSeatsRepo.saveAll(showSeats);
        movieTheaterRepo.save(new MovieTheaters(movie, new Theater(movieDTO.getFkTheater(), null, 0, null, null), movieDTO.getPrice(), 0L));
    }

    @Transactional
    private void addMovie(NewMovieDTO movieDTO, Hall hall) {
        Movie movie = new Movie();
        var shows = toShowList(movieDTO.getShows(), movie, hall);
        movie.setShows(shows);
        movie.setName(movieDTO.getMovieName());
        var showSeats = toShowSeatsList(movie.getShows(), hall.getSeats());
        movie.setShows(movie.getShows());
        movie.getShows().forEach(e -> e.setFkMovie(movie));
        movieRepo.save(movie);
        showSeatsRepo.saveAll(showSeats);
        movieTheaterRepo.save(new MovieTheaters(movie, new Theater(movieDTO.getFkTheater(), null, 0, null, null), movieDTO.getPrice(), 0L));
    }

    private List<Show> toShowList(List<AddShowsDTO> addShowsDTOS, Movie movie, Hall hall) {
        return addShowsDTOS.stream().map(e -> {
            Show show = new Show();
            show.setDate(e.getDate());
            show.setFkHall(hall);
            show.setFkMovie(movie);
            show.setStartTime(e.getStartTime());
            show.setEndTime(e.getEndTime());
            return show;
        }).toList();
    }

    private List<ShowSeat> toShowSeatsList(List<Show> shows, List<Seat> seats) {
        return shows.stream().map(e -> seats.stream().map(m -> {
            var showSeats = new ShowSeat();
            showSeats.setFkSeats(m);
            showSeats.setFkShow(e);
            return showSeats;
        }).toList()).flatMap(List::stream).toList();
    }


    private boolean minSeatsCheck(List<AddSeatsDTO> addSeatDTOS) {
        for (AddSeatsDTO addSeatsDTO : addSeatDTOS) {
            if (addSeatsDTO.getSeats().size() < 6) {
                return false;
            }
        }
        return true;
    }

    private List<Hall> toHallList(List<NewHallDTO> newHallDTO) {
        return newHallDTO.stream().map(e -> {
            Hall hall = new Hall();
            hall.setName(e.getName());
            hall.setRows(e.getRows());
            hall.setColumns(e.getColumns());
            hall.setTotalSeats(e.getTotalSeats());
            hall.setSeats(toSeatList(e.getSeats()));
            return hall;
        }).toList();
    }

    private List<Seat> toSeatList(List<AddSeatsDTO> dto) {
        return dto.stream().map((e -> e.getSeats().stream().map(m -> {
            Seat seats = new Seat();
            seats.setRowNumber(e.getRowNumber());
            seats.setSeatNumber(m);
            return seats;
        }).toList())).flatMap(List::stream).toList();
    }


    public boolean updateMoviePrice(UpdateSeatPrice updateSeatPrice) {
        MovieTheaters movie = null;
        try {
            movie = movieTheaterRepo.findById(updateSeatPrice.getId()).orElseThrow(()->new Exception("movieTheater is null"));
        } catch (Exception e) {
           return  false;
        }
        movie.setPrice(updateSeatPrice.getPrice());
        movieTheaterRepo.save(movie);
        return true;
    }
}
