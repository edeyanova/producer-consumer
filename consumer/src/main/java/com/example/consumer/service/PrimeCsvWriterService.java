package com.example.consumer.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for writing prime numbers to a CSV file.
 * <p>
 * This service handles the creation and appending of prime numbers to a CSV file. It ensures that the prime
 * numbers are properly formatted and appended to the file with a comma delimiter between numbers.
 * </p>
 */
@Service
public class PrimeCsvWriterService {
    private final Logger logger;
    private String csvFilePath;

    public PrimeCsvWriterService(Logger logger) {
        this.logger = logger;
    }

    @Value("${csv.file.path}")
    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    /**
     * Writes a list of prime numbers to the CSV file.
     * <p>
     * The method checks if the file exists and creates it if it does not. It appends the prime numbers to the file,
     * separated by commas. If the file already contains data, a comma is added before appending the new data.
     * </p>
     *
     * @param primes A list of prime numbers to be written to the CSV file.
     */
    public void writePrimesToCsv(List<Integer> primes) {
        File file = new File(csvFilePath);
        boolean fileExists = file.exists();
        boolean fileHasContent = fileExists && file.length() > 0;

        String primeData = primes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        try {
            if (!fileExists) {
                file.createNewFile();
            }

            try (FileWriter writer = new FileWriter(csvFilePath, true)) {
                if (fileHasContent) {
                    writer.append(",").append(primeData);
                } else {
                    writer.write(primeData);
                }
                writer.flush();
                logger.info("Prime numbers written to CSV: {}", primeData);
            }
        } catch (IOException e) {
            logger.error("Error writing prime numbers to CSV file '{}': {}", csvFilePath, e.getMessage(), e);
        }
    }
}
