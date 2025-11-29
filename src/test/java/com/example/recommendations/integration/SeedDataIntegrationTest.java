package com.example.recommendations.integration;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SeedDataIntegrationTest {

    @Test
    void seedProducesDbFile() throws Exception {
        Path db = Path.of("data/recommendations.db");
        if (!Files.exists(db)) {
            ProcessBuilder pb = new ProcessBuilder("./scripts/seed-mock-data.sh", db.toString());
            pb.inheritIO();
            Process p = pb.start();
            int rc = p.waitFor();
            if (rc != 0) throw new RuntimeException("Seed failed: " + rc);
        }
        assertTrue(Files.exists(db), "Expected DB file to exist after seeding");
    }
}
