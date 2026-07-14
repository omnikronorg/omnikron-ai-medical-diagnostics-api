package org.omnikron.aimda.repository;

import org.springframework.stereotype.Repository;

@Repository
public class PredictionCoreRepository {
    public String GetHealth()
    {
        return "{\"status\":\"ok\"}";
    }
}
