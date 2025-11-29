package com.example.recommendations.integration;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RecommendationIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @BeforeAll
    static void seed() throws Exception {
        Path db = Path.of("data/recommendations.db");
        Files.createDirectories(db.getParent());
        // Run seed SQL via script if present
        ProcessBuilder pb = new ProcessBuilder("./scripts/seed-mock-data.sh", db.toString());
        pb.inheritIO();
        Process p = pb.start();
        int rc = p.waitFor();
        if (rc != 0) throw new RuntimeException("Seed failed: " + rc);
    }

    @Test
    void getRecommendations_singleDate_returns200() throws Exception {
        mvc.perform(get("/recommendations?origin=JFK&destination=LAX&date=2025-12-01")).andExpect(status().isOk());
    }

    @Test
    void getRecommendations_range_returns200() throws Exception {
        mvc.perform(get("/recommendations?origin=JFK&destination=LAX&start_date=2025-12-01&end_date=2025-12-05")).andExpect(status().isOk());
    }
}
