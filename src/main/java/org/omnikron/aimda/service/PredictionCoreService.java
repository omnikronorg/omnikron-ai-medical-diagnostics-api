package org.omnikron.aimda.service;

import java.io.IOException;

import org.omnikron.aimda.model.PredictionResult;
import org.omnikron.aimda.repository.PredictionCoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class PredictionCoreService {
    private final PredictionCoreRepository predictionCoreRepository;

    public PredictionCoreService(PredictionCoreRepository predictionCoreRepository)
    {
        this.predictionCoreRepository = predictionCoreRepository;
    }

    public String GetHealthStatus()
    {
        String healthJSONRaw = predictionCoreRepository.GetHealth();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(healthJSONRaw);

        String status = rootNode.get("status").asString();

        return status;
    }

    public PredictionResult Predict(MultipartFile image) throws IOException 
    {
        byte[] imageBytes = image.getBytes();
        String fileName = image.getOriginalFilename();

        String predictionJSONRaw = predictionCoreRepository.Predict(imageBytes, fileName);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(predictionJSONRaw);

        String prediction = rootNode.get("prediction").asString();
        double probabilityPneumonia = rootNode.get("probability_pneumonia").asDouble();

        return new PredictionResult(prediction, probabilityPneumonia);
    }

}
