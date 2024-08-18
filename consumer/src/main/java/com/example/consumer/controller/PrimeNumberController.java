package com.example.consumer.controller;

import com.example.consumer.service.PrimeNumberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PrimeNumberController {

    private final PrimeNumberService primeNumberService;

    public PrimeNumberController(PrimeNumberService primeNumberService) {
        this.primeNumberService = primeNumberService;
    }

    @PostMapping("/consume")
    public void consumeNumbers(@RequestBody List<Integer> numbers) {
        primeNumberService.processNumbers(numbers);
    }
}
