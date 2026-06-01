package com.example.currencyconverter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true) // Игнорируем все остальные 20+ полей, которые нам не нужны
public record CurrencyResponse(
        String result,

        @JsonProperty("base_currency_code") // Защита на случай, если ключ называется иначе
        String base_code,

        @JsonProperty("rates")
        Map<String, Double> rates
) {}