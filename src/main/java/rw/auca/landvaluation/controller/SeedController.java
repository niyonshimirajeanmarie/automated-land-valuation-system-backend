package rw.auca.landvaluation.controller;

import rw.auca.landvaluation.service.SeedDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seed")
public class SeedController {

    private final SeedDataService seedDataService;

    public SeedController(SeedDataService seedDataService) {
        this.seedDataService = seedDataService;
    }

    @PostMapping
    public ResponseEntity<String> seed() {
        return ResponseEntity.ok(seedDataService.seed());
    }
}
