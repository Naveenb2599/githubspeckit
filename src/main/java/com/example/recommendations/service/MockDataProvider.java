package com.example.recommendations.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.recommendations.model.Fare;
import com.example.recommendations.model.Flight;

@Component
public class MockDataProvider {

    private final List<Flight> flights = new ArrayList<>();
    private final List<Fare> fares = new ArrayList<>();

    public MockDataProvider() {
        // create 5 days of mock flights and fares
        String[] dates = {"2025-12-01","2025-12-02","2025-12-03","2025-12-04","2025-12-05"};
        int idx = 1;
        for (String d : dates) {
            String flightId = String.format("F%dA", idx++);
            Flight f = new Flight();
            f.setFlightId(flightId);
            f.setAirline("EX");
            f.setOrigin("JFK");
            f.setDestination("LAX");
            f.setDate(d);
            f.setDepartureTime("08:00");
            f.setArrivalTime("11:00");
            f.setDurationMinutes(300);
            flights.add(f);

            // fares per flight: saver, roundtrip_saver, flex, business, first_class
            fares.add(createFare(flightId, "saver", 19900));
            fares.add(createFare(flightId, "roundtrip_saver", 35900));
            fares.add(createFare(flightId, "flex", 49900));
            fares.add(createFare(flightId, "business", 129900));
            fares.add(createFare(flightId, "first_class", 199900));
        }
    }

    private Fare createFare(String flightId, String type, int priceCents) {
        Fare fare = new Fare();
        fare.setId((long)(Math.abs((flightId + type).hashCode())));
        fare.setFlightId(flightId);
        fare.setFareType(type);
        fare.setPriceCents(priceCents);
        fare.setCurrency("USD");
        fare.setRefundable(false);
        fare.setRules(type + " rules");
        return fare;
    }

    public List<Flight> findFlightsByOriginDestinationDate(String origin, String destination, String date) {
        return flights.stream()
                .filter(f -> f.getOrigin().equalsIgnoreCase(origin) && f.getDestination().equalsIgnoreCase(destination) && f.getDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<Flight> findFlightsByOriginDestinationDateRange(String origin, String destination, String start, String end) {
        LocalDate s = LocalDate.parse(start);
        LocalDate e = LocalDate.parse(end);
        return flights.stream()
                .filter(f -> {
                    LocalDate fd = LocalDate.parse(f.getDate());
                    return f.getOrigin().equalsIgnoreCase(origin) && f.getDestination().equalsIgnoreCase(destination) && (fd.equals(s) || fd.equals(e) || (fd.isAfter(s) && fd.isBefore(e)));
                })
                .collect(Collectors.toList());
    }

    public List<Fare> findFaresByFlightId(String flightId) {
        return fares.stream().filter(f -> f.getFlightId().equals(flightId)).collect(Collectors.toList());
    }

    public Flight findFlightById(String flightId) {
        return flights.stream().filter(f -> f.getFlightId().equals(flightId)).findFirst().orElse(null);
    }
}
