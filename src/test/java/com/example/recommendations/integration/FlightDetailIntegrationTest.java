package com.example.recommendations.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightDetailIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getFlightDetails_returns200() throws Exception {
        // Use an id from the seed data
        mvc.perform(get("/flights/F20251201A")).andExpect(status().isOk());
    }
}
