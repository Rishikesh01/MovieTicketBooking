package com.ticketbooking.org.demo.repository;

import com.ticketbooking.org.demo.model.Movie;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepo extends JpaRepository<Movie,Integer> {
   Optional< Movie> findByNameContaining(String name);

   @Transactional
   @Query(value = "insert into movie_theaters(fk_theater_id,movie_id) values(:id,:mID)",nativeQuery = true)
   void update(@Param("id") int id,@Param("mID") int mvID);
   Optional<Movie> findByName(String name);
}
