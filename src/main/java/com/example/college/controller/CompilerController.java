package com.example.college.controller;

import com.example.college.dto.CodeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/compiler") // ✅ fixed spelling
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CompilerController {

    private static final Logger logger = LoggerFactory.getLogger(CompilerController.class);

    // 🔑 Replace with your JDoodle credentials
    private final String JD_CLIENT_ID = "c674d34e27299c0d4a2b0f4deac36f59";
    private final String JD_CLIENT_SECRET = "21951d735c80a5aba2018e341137de27228033c2ac8dc5645800b29d72263018";

    private final String JD_URL = "https://api.jdoodle.com/v1/execute";

    @PostMapping("/run")
    public ResponseEntity<?> runCode(@RequestBody CodeRequest request) {
        logger.info("Received code execution request: language={}, versionIndex={}", 
                request.getLanguage(), request.getVersionIndex());

        // ✅ Basic validation
        if (request.getScript() == null || request.getScript().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Code script cannot be empty"));
        }
        if (request.getLanguage() == null || request.getLanguage().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Language is required"));
        }
        if (request.getVersionIndex() == null || request.getVersionIndex().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Version index is required"));
        }

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();
        body.put("clientId", JD_CLIENT_ID);
        body.put("clientSecret", JD_CLIENT_SECRET);
        body.put("script", request.getScript());
        body.put("language", request.getLanguage());
        body.put("versionIndex", request.getVersionIndex());
        body.put("stdin", request.getStdin() != null ? request.getStdin() : ""); // ✅ Include user input

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(JD_URL, entity, String.class);
            logger.info("JDoodle response status: {}", response.getStatusCode());
            return ResponseEntity.ok(response.getBody());

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("JDoodle API error: {}", e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of("error", "JDoodle API error", "details", e.getResponseBodyAsString()));

        } catch (ResourceAccessException e) {
            logger.error("JDoodle API unreachable: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                    .body(Map.of("error", "JDoodle API unreachable. Check internet/endpoint."));

        } catch (Exception e) {
            logger.error("Unexpected error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unexpected error occurred while executing code"));
        }
    }

}
