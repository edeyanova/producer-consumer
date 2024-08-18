package com.example.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for processing numbers to identify and handle prime numbers.
 * <p>
 * This service takes a list of numbers, filters out the prime numbers, and writes them to a CSV file.
 * </p>
 */
@Service
public class PrimeNumberService {
    private static final Logger logger = LoggerFactory.getLogger(PrimeNumberService.class);

    private final PrimeCsvWriterService primeCsvWriterService;

    public PrimeNumberService(PrimeCsvWriterService primeCsvWriterService) {
        this.primeCsvWriterService = primeCsvWriterService;
    }


    /**
     * Processes a list of integers to filter and write prime numbers to a CSV file.
     *
     * @param numbers A list of integers to be processed.
     */
    public void processNumbers(List<Integer> numbers) {
        List<Integer> primes = numbers.stream()
                .filter(this::isPrime)
                .toList();
        if (!primes.isEmpty()) {
            this.primeCsvWriterService.writePrimesToCsv(primes);
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}
