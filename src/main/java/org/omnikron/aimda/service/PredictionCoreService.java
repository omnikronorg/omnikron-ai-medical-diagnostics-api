package org.omnikron.aimda.service;

import org.omnikron.aimda.repository.PredictionCoreRepository;
import org.springframework.stereotype.Service;

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
}
