package com.example.consumer.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrimeCsvWriterServiceTest {

    @Mock
    private Logger logger;

    @InjectMocks
    private PrimeCsvWriterService primeCsvWriterService;

    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        tempFile = Files.createTempFile("prime_numbers", ".csv").toFile();
        primeCsvWriterService.setCsvFilePath(tempFile.getAbsolutePath());
    }

    @AfterEach
    public void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void testWritePrimesToCsv_FileNotExists() throws Exception {
        tempFile.delete();

        List<Integer> primes = Arrays.asList(2, 3, 5, 7);

        primeCsvWriterService.writePrimesToCsv(primes);

        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);
        verify(logger).info(logCaptor.capture(), eq("2,3,5,7"));
        assertTrue(logCaptor.getValue().contains("Prime numbers written to CSV:"));

        String content = new String(Files.readAllBytes(Paths.get(tempFile.getAbsolutePath())));
        assertTrue(content.contains("2,3,5,7"));
    }

    @Test
    public void testWritePrimesToCsv_FileExists() throws Exception {
        tempFile.createNewFile();

        List<Integer> primes = Arrays.asList(2, 3, 5, 7);

        primeCsvWriterService.writePrimesToCsv(primes);

        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);
        verify(logger).info(logCaptor.capture(), eq("2,3,5,7"));
        assertTrue(logCaptor.getValue().contains("Prime numbers written to CSV:"));

        String content = new String(Files.readAllBytes(Paths.get(tempFile.getAbsolutePath())));
        assertTrue(content.contains("2,3,5,7"));
    }
}
