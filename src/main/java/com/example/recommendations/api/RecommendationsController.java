package com.example.recommendations.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.recommendations.dto.RecommendationDto;
import com.example.recommendations.service.RecommendationService;
import com.example.recommendations.validation.RequestValidators;

@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {

    private final RecommendationService recommendationService;

    public RecommendationsController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getRecommendations(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String start_date,
            @RequestParam(required = false) String end_date
    ) {
        RequestValidators.validateIata(origin);
        RequestValidators.validateIata(destination);

        List<RecommendationDto> results;
        if (date != null && !date.isBlank()) {
            RequestValidators.parseDate(date);
            results = recommendationService.recommendForDate(origin, destination, date);
        } else {
            // range required
            if (start_date == null || end_date == null) {
                throw new IllegalArgumentException("Either date or start_date and end_date must be provided");
            }
            RequestValidators.parseDate(start_date);
            RequestValidators.parseDate(end_date);
            results = recommendationService.recommendForRange(origin, destination, start_date, end_date);
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("results", results);
        return ResponseEntity.ok(resp);
    }
}
