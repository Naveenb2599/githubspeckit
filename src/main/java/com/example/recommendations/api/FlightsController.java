package com.example.recommendations.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.recommendations.dto.FareDto;
import com.example.recommendations.dto.FlightDto;
import com.example.recommendations.mapper.FlightMapper;
import com.example.recommendations.model.Fare;
import com.example.recommendations.model.Flight;
import com.example.recommendations.service.FlightService;

@RestController
@RequestMapping("/flights")
public class FlightsController {

    private final FlightService flightService;

    public FlightsController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<Map<String, Object>> getFlight(@PathVariable("flightId") String flightId) {
        Flight flight = flightService.getFlightById(flightId);
        List<Fare> fares = flightService.getFaresForFlight(flightId);

        FlightDto fd = FlightMapper.toDto(flight);
        List<FareDto> faresDto = fares.stream().map(FlightMapper::fareToDto).collect(Collectors.toList());

        Map<String, Object> resp = new HashMap<>();
        resp.put("flight", fd);
        resp.put("fares", faresDto);
        return ResponseEntity.ok(resp);
    }
}
