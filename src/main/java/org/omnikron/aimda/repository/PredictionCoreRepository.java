package org.omnikron.aimda.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@Repository
public class PredictionCoreRepository {

    @Value("${CORE_BASE_URL}")
    private String coreBaseURL;

    @Value("${CORE_PORT}")
    private String corePort;


    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory() {{
            setConnectTimeout(5000);
            setReadTimeout(5000);
        }});
    }

    private String ToCoreURLPath(String endpoint)
    {
        return coreBaseURL + ":" + corePort + endpoint;
    }
    
    public String GetHealth()
    {
        // Call the ${CORE_ENDPOINT}:${CORE_PORT}/health - eg. curl -X GET http://localhost:5000/health
        // Expected response: {"status":"ok"}

        String urlPath = ToCoreURLPath("/health");
        String response = restTemplate.getForObject(urlPath, String.class);
        return response;
    }

}
