package com.example.currencyconverter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers; // Явный импорт матчеров

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyConverterApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetIndexPageSuccess() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index")); // Теперь IDEA точно поймет, чей это метод
    }

    @Test
    public void testPostConvertCurrencySuccess() throws Exception {
        mockMvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("amount", "100")
                        .param("fromCurrency", "USD")
                        .param("toCurrency", "USD"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("amount"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("result"))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Результат конвертации")));
    }
}