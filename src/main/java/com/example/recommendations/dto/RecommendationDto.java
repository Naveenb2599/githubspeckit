package com.example.recommendations.dto;

import java.util.List;

public class RecommendationDto {
    private FlightDto flight;
    private List<FareDto> fares;
    private double score;

    public FlightDto getFlight() { return flight; }
    public void setFlight(FlightDto flight) { this.flight = flight; }
    public List<FareDto> getFares() { return fares; }
    public void setFares(List<FareDto> fares) { this.fares = fares; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
}
