package com.ticketbooking.org.demo.service;


import com.ticketbooking.org.demo.dto.user.MovieHallSeatsDTO;
import com.ticketbooking.org.demo.dto.user.MoviesDTO;
import com.ticketbooking.org.demo.dto.user.ShowsDTO;
import com.ticketbooking.org.demo.dto.user.TheatersDTO;
import com.ticketbooking.org.demo.model.Movie;
import com.ticketbooking.org.demo.model.MovieTheaters;
import com.ticketbooking.org.demo.repository.MovieRepo;
import com.ticketbooking.org.demo.repository.ShowRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MovieService {
    private final MovieRepo movieRepo;

    private final ShowRepo showRepo;

    public MoviesDTO getListOfTheater(String name) throws Exception {
        var movie = movieRepo.findByNameContaining(name).orElseThrow(() -> new Exception("null"));
        MoviesDTO moviesDTO = new MoviesDTO();
        moviesDTO.setId(movie.getId());
        moviesDTO.setName(movie.getName());
        var list = toTheatersDTOS(movie.getMovieTheaters(), movie);
        moviesDTO.setTheater(list);
        return moviesDTO;
    }

    private List<TheatersDTO> toTheatersDTOS(List<MovieTheaters> movieTheaters, Movie movie) {
        return movieTheaters.stream().map(e -> {
            var theater = new TheatersDTO();
            theater.setId(e.getTheater().getId());
            theater.setShows(movie.getShows().stream().filter(m -> m.getFkHall().getFkTheater().getId() == e.getTheater().getId()).map(m -> {
                var show = new ShowsDTO();
                show.setId(m.getId());
                show.setHallID(m.getFkHall().getId());
                show.setStartTime(m.getStartTime());
                show.setHallName(m.getFkHall().getName());
                return show;
            }).toList());
            return theater;
        }).toList();
    }

    public Optional<List<MovieHallSeatsDTO>> getShowSeats(int showID) {
        var show = showRepo.findById(showID);
        if (show.isEmpty()) {
            return Optional.empty();
        }
        var seats = show.get().getShowSeats().stream().map(e -> {
            var hallSeats = new MovieHallSeatsDTO();
            hallSeats.setId(e.getId());
            hallSeats.setRow(e.getFkSeats().getRowNumber());
            hallSeats.setSeat(e.getFkSeats().getSeatNumber());
            return hallSeats;
        }).toList();
        return Optional.of(seats);
    }
}
