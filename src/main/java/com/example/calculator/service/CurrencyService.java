package com.example.calculator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
public class CurrencyService {

    private final WebClient client = WebClient.create("https://api.exchangerate-api.com");

    public double convert(double amount, String from, String to) {
        try {
            Map data = client.get()
                    .uri("/v4/latest/" + from)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (data == null) return 0;

            Map rates = (Map) data.get("rates");
            return amount * Double.parseDouble(rates.get(to).toString());

        } catch (Exception e) {
            return 0;
        }
    }
}
