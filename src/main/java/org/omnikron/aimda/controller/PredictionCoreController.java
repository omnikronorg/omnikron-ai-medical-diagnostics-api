package org.omnikron.aimda.controller;

import org.omnikron.aimda.model.PredictionCoreHealth;
import org.omnikron.aimda.service.PredictionCoreService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/core")
public class PredictionCoreController {
    private final PredictionCoreService predictionCoreService;

    public PredictionCoreController(PredictionCoreService predictionCoreService)
    {
        this.predictionCoreService = predictionCoreService;
    }

    @GetMapping("/health")
    public ResponseEntity<?> getPredictionCoreHealth() {
        String healthStatus = predictionCoreService.GetHealthStatus();

        PredictionCoreHealth predictionCoreHealth = new PredictionCoreHealth(healthStatus);
        return ResponseEntity.ok(predictionCoreHealth);

    }
    
}
