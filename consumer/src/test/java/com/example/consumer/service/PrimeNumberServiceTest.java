package com.example.consumer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrimeNumberServiceTest {
    @Mock
    private PrimeCsvWriterService primeCsvWriterService;

    @InjectMocks
    private PrimeNumberService primeNumberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        primeNumberService = new PrimeNumberService(primeCsvWriterService);
    }

    @Test
    public void testProcessNumbers_WithPrimeAndNonPrime() {
        List<Integer> numbers = Arrays.asList(2, 4, 5, 6, 11);

        primeNumberService.processNumbers(numbers);

        List<Integer> expectedPrimes = Arrays.asList(2, 5, 11);
        verify(primeCsvWriterService, times(1)).writePrimesToCsv(expectedPrimes);

        verify(primeCsvWriterService, never()).writePrimesToCsv(Arrays.asList(4, 6));
    }

    @Test
    public void testProcessNumbers_AllNonPrime() {
        List<Integer> numbers = Arrays.asList(4, 6, 8, 9);

        primeNumberService.processNumbers(numbers);

        verify(primeCsvWriterService, never()).writePrimesToCsv(anyList());
    }

    @Test
    public void testProcessNumbers_EmptyList() {
        List<Integer> numbers = Arrays.asList();

        primeNumberService.processNumbers(numbers);

        verify(primeCsvWriterService, never()).writePrimesToCsv(anyList());
    }
}
