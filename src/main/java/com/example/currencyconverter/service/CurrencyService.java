package com.example.currencyconverter.service;

import com.example.currencyconverter.dto.CurrencyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CurrencyService {

    // Самая надежная резервная ссылка (работает без ключей по всему миру)
    private static final String API_URL = "https://open.er-api.com/v6/latest/USD";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CurrencyResponse getExchangeRates() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Выводим ответ сервера в консоль IDEA, чтобы мы могли его глазами QA увидеть
            System.out.println("ОТВЕТ ОТ API СЕРВЕРА: " + response.body());

            // Читаем JSON в наш рекорд
            return objectMapper.readValue(response.body(), CurrencyResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось получить курсы валют из сети: " + e.getMessage());
        }
    }

    public double convert(String from, String to, double amount) {
        CurrencyResponse data = getExchangeRates();

        // Проверяем, что дата пришла и внутри нее есть заполненная Map с курсами
        if (data == null || data.rates() == null || data.rates().isEmpty()) {
            throw new IllegalArgumentException("Данные о курсах валют пустые или не распарсились.");
        }

        double rateFrom = data.rates().getOrDefault(from, 1.0);
        double rateTo = data.rates().getOrDefault(to, 1.0);

        double amountInUSD = amount / rateFrom;
        return amountInUSD * rateTo;
    }
}