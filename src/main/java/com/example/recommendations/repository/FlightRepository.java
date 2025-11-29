package com.example.recommendations.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.recommendations.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
    List<Flight> findByOriginAndDestinationAndDate(String origin, String destination, String date);
    List<Flight> findByOriginAndDestinationAndDateBetween(String origin, String destination, String start, String end);
}
