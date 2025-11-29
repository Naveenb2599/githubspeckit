package com.example.recommendations.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.recommendations.model.Fare;
import com.example.recommendations.model.Flight;
import com.example.recommendations.repository.FareRepository;
import com.example.recommendations.repository.FlightRepository;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FareRepository fareRepository;
    private final MockDataProvider mockDataProvider;

    private final boolean useMock;

    public FlightService(FlightRepository flightRepository, FareRepository fareRepository, MockDataProvider mockDataProvider, @Value("${app.use-mock:false}") boolean useMock) {
        this.flightRepository = flightRepository;
        this.fareRepository = fareRepository;
        this.mockDataProvider = mockDataProvider;
        this.useMock = useMock;
    }

    public Flight getFlightById(String flightId) {
        if (useMock) {
            Flight f = mockDataProvider.findFlightById(flightId);
            if (f == null) throw new java.util.NoSuchElementException("Flight not found: " + flightId);
            return f;
        }
        return flightRepository.findById(flightId).orElseThrow(() -> new java.util.NoSuchElementException("Flight not found: " + flightId));
    }

    public List<Fare> getFaresForFlight(String flightId) {
        if (useMock) {
            return mockDataProvider.findFaresByFlightId(flightId);
        }
        return fareRepository.findByFlightId(flightId);
    }
}
