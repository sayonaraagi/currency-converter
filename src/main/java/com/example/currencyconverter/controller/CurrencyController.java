package com.example.currencyconverter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.currencyconverter.service.CurrencyService;

@Controller
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/convert")
    public String convertCurrency(
            @RequestParam double amount,
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            Model model) {
        try {
            double result = currencyService.convert(fromCurrency, toCurrency, amount);

            model.addAttribute("amount", amount);
            model.addAttribute("fromCurrency", fromCurrency);
            model.addAttribute("toCurrency", toCurrency);
            model.addAttribute("result", String.format("%.2f", result));
        } catch (Exception e) {
            model.addAttribute("error", "Произошла ошибка: " + e.getMessage());
        }
        return "index";
    }
}
