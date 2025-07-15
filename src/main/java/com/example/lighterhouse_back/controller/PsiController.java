package com.example.lighterhouse_back.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.example.lighterhouse_back.util.PsiUtils.extractMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@RequestMapping("/api") 
public class PsiController {
    private static final Logger logger = LoggerFactory.getLogger(PsiController.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${google.psi.key}")
    private String psiKey;


    // @PostConstruct
    // public void init() {
    //     logger.info("✅ Loaded PSI Key: {}", psiKey);
    // }

    @GetMapping("/analyze")
    public ResponseEntity<?> analyze(@RequestParam String url) {
        try {
            // logger.info("Received URL for analysis: {}", url);

            String endpoint = "https://www.googleapis.com/pagespeedonline/v5/runPagespeed?url=" + url +
                "&strategy=desktop&category=performance&category=accessibility&category=seo&category=best-practices&key=" + psiKey;

            // logger.info("Requesting PSI endpoint: {}", endpoint);

            Map<String, Object> result = restTemplate.getForObject(endpoint, Map.class);
            Map<String, Object> lighthouseResult = (Map<String, Object>) result.get("lighthouseResult");
            return ResponseEntity.ok(lighthouseResult);

        } catch (HttpStatusCodeException e) {
            String responseBody = e.getResponseBodyAsString();
            logger.error("PSI API returned error: {}", responseBody, e);

            return ResponseEntity
                .status(e.getStatusCode())
                .body(Map.of("error", "PSI 요청 실패: " + extractMessage(responseBody)));
        } catch (Exception e) {
            logger.error("Unexpected error during PSI analysis", e);
            return ResponseEntity.status(500).body(Map.of("error", "예상치 못한 오류가 발생했습니다."));
            
        }
    }
}
