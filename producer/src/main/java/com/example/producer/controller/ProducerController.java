package com.example.producer.controller;

import com.example.producer.service.NumberGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    private final NumberGeneratorService numberGeneratorService;

    public ProducerController(NumberGeneratorService numberGeneratorService) {
        this.numberGeneratorService = numberGeneratorService;
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generateNumbers() {
        numberGeneratorService.generateNumbers();
        return ResponseEntity.ok("Numbers generated and sent to consumer.");
    }
}
