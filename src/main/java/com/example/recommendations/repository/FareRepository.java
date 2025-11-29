package com.example.recommendations.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.recommendations.model.Fare;

@Repository
public interface FareRepository extends JpaRepository<Fare, Long> {
    List<Fare> findByFlightId(String flightId);
}
