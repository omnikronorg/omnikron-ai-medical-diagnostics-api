package org.omnikron.aimda.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    public String Predict(byte[] imageBytes, String fileName)
    {
        // Call the ${CORE_ENDPOINT}:${CORE_PORT}/predict - eg. curl -X POST -F "image=@normal_0125.jpeg" http://127.0.0.1:5000/predict
        // Expected response: {"prediction":"PNEUMONIA","probability_pneumonia":1.0}

        String urlPath = ToCoreURLPath("/predict");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        
        ByteArrayResource resource = new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return fileName;
            }
        };

        body.add("image", resource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String response = restTemplate.postForObject(urlPath, requestEntity, String.class);

        return response;
    }

}
