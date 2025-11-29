package com.example.recommendations.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.recommendations.dto.RecommendationDto;
import com.example.recommendations.mapper.FlightMapper;
import com.example.recommendations.model.Fare;
import com.example.recommendations.model.Flight;
import com.example.recommendations.repository.FareRepository;
import com.example.recommendations.repository.FlightRepository;

@Service
public class RecommendationService {

    private final FlightRepository flightRepository;
    private final FareRepository fareRepository;
    private final MockDataProvider mockDataProvider;

    private final boolean useMock;

    public RecommendationService(FlightRepository flightRepository, FareRepository fareRepository, MockDataProvider mockDataProvider, @Value("${app.use-mock:false}") boolean useMock) {
        this.flightRepository = flightRepository;
        this.fareRepository = fareRepository;
        this.mockDataProvider = mockDataProvider;
        this.useMock = useMock;
    }

    public List<RecommendationDto> recommendForDate(String origin, String destination, String date) {
        List<Flight> flights;
        if (useMock) {
            flights = mockDataProvider.findFlightsByOriginDestinationDate(origin, destination, date);
        } else {
            flights = flightRepository.findByOriginAndDestinationAndDate(origin, destination, date);
        }
        return toRecommendations(flights);
    }

    public List<RecommendationDto> recommendForRange(String origin, String destination, String start, String end) {
        List<Flight> flights;
        if (useMock) {
            flights = mockDataProvider.findFlightsByOriginDestinationDateRange(origin, destination, start, end);
        } else {
            flights = flightRepository.findByOriginAndDestinationAndDateBetween(origin, destination, start, end);
        }
        return toRecommendations(flights);
    }

    private List<RecommendationDto> toRecommendations(List<Flight> flights) {
        List<RecommendationDto> out = new ArrayList<>();
        for (Flight f : flights) {
            List<Fare> fares;
            if (useMock) {
                fares = mockDataProvider.findFaresByFlightId(f.getFlightId());
            } else {
                fares = fareRepository.findByFlightId(f.getFlightId());
            }
            out.add(FlightMapper.toRecommendation(f, fares));
        }
        return out;
    }
}
