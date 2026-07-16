package org.omnikron.aimda.controller;

import java.io.IOException;

import org.omnikron.aimda.model.PredictionCoreHealth;
import org.omnikron.aimda.model.PredictionResult;
import org.omnikron.aimda.service.PredictionCoreService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



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

    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestParam("image") MultipartFile image) {
        try
        {
            PredictionResult result = predictionCoreService.Predict(image);
            return ResponseEntity.ok(result);
        }
        catch(IOException e)
        {
            return ResponseEntity.badRequest().body("Failed to read the uploaded image: " + e.getMessage());
        }
    }
    
    
}
