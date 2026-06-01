package com.example.currencyconverter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrencyResponse(
        String result,

        @JsonProperty("base_currency_code")
        String base_code,

        @JsonProperty("rates")
        Map<String, Double> rates
) {}