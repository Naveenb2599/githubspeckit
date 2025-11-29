package com.example.recommendations.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.recommendations.dto.FareDto;
import com.example.recommendations.dto.FlightDto;
import com.example.recommendations.dto.RecommendationDto;
import com.example.recommendations.model.Fare;
import com.example.recommendations.model.Flight;

public class FlightMapper {
    public static FlightDto toDto(Flight f) {
        FlightDto dto = new FlightDto();
        dto.setFlightId(f.getFlightId());
        dto.setAirline(f.getAirline());
        dto.setOrigin(f.getOrigin());
        dto.setDestination(f.getDestination());
        dto.setDate(f.getDate());
        dto.setDepartureTime(f.getDepartureTime());
        dto.setArrivalTime(f.getArrivalTime());
        dto.setDurationMinutes(f.getDurationMinutes());
        return dto;
    }

    public static FareDto fareToDto(Fare f) {
        FareDto dto = new FareDto();
        dto.setFareType(f.getFareType());
        dto.setPrice(f.getPriceCents() / 100.0);
        dto.setCurrency(f.getCurrency());
        dto.setRefundable(Boolean.TRUE.equals(f.getRefundable()));
        dto.setRules(f.getRules());
        return dto;
    }

    public static RecommendationDto toRecommendation(Flight flight, List<Fare> fares) {
        RecommendationDto r = new RecommendationDto();
        r.setFlight(toDto(flight));
        List<FareDto> fd = fares.stream().map(FlightMapper::fareToDto).collect(Collectors.toList());
        r.setFares(fd);
        // Simple score: lower saver price => higher score
        double score = fd.stream().mapToDouble(fa -> {
            if ("saver".equals(fa.getFareType())) return -fa.getPrice();
            return 0.0;
        }).min().orElse(0.0);
        r.setScore(-score);
        return r;
    }
}
