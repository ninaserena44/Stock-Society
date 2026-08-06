package com.stocksociety.stocksociety.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class SupplierServiceClient {

    private final RestTemplate restTemplate;

    public SupplierServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getSuppliers() {
        try {

                HttpHeaders headers = new HttpHeaders();

                String auth = "admin:admin123";

                byte[] encodedAuth = Base64.getEncoder()
                        .encode(auth.getBytes(StandardCharsets.UTF_8));

                headers.set(
                        HttpHeaders.AUTHORIZATION,
                        "Basic " + new String(encodedAuth)
                );

                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<List<Map<String, Object>>> response =
                restTemplate.exchange(
                        "http://localhost:8081/suppliers",
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<List<Map<String, Object>>>() {}
                );

                return response.getBody();

        } catch (RestClientException e) {

                return List.of();

        }
    }
}