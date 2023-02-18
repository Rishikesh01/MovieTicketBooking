package com.ticketbooking.org.demo.service;


import com.ticketbooking.org.demo.dto.user.MovieHallSeat;
import com.ticketbooking.org.demo.dto.user.MoviesDTO;
import com.ticketbooking.org.demo.dto.user.ShowsDTO;
import com.ticketbooking.org.demo.dto.user.TheatersDTO;
import com.ticketbooking.org.demo.repository.MovieRepo;
import com.ticketbooking.org.demo.repository.ShowRepo;
import com.ticketbooking.org.demo.repository.ShowSeatsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class MovieService {
    private final MovieRepo movieRepo;

    private final ShowSeatsRepo showSeatsRepo;
    private final ShowRepo showRepo;

    public MoviesDTO getListOfTheater(String name) throws Exception {
        var movie = movieRepo.findByNameLike(name).orElseThrow(() -> new Exception("null"));
        MoviesDTO moviesDTO = new MoviesDTO();
        moviesDTO.setId(movie.getId());
        moviesDTO.setName(name);
        var list = movie.getFkTheater().parallelStream().collect(Collectors.toSet()).parallelStream().map(e -> {
            var theater = new TheatersDTO();
            theater.setId(e.getId());
            theater.setShows(movie.getShows().stream().filter(m -> m.getFkHall().getFkTheater().getId() == e.getId()).map(m -> {
                var show = new ShowsDTO();
                show.setId(m.getId());
                show.setHallID(m.getFkHall().getId());
                show.setStartTime(m.getStartTime());
                show.setHallName(m.getFkHall().getName());
                return show;
            }).toList());
            return theater;
        }).toList();


        moviesDTO.setTheater(list);

        return moviesDTO;
    }

    public Optional<List<MovieHallSeat>> getShowSeats(int showID) {
        var show = showRepo.findById(showID);
        if (show.isEmpty()) {
            return Optional.empty();
        }
        var seats = show.get().getShowSeats().parallelStream().map(
                e -> {
                    var hallSeats = new MovieHallSeat();
                    hallSeats.setId(e.getId());
                    hallSeats.setRow(e.getFkSeats().getRowNumber());
                    hallSeats.setSeat(e.getFkSeats().getSeatNumber());
                    return hallSeats;
                }
        ).toList();
        return Optional.of(seats);
    }
}
