package com.example.producer.service;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvWriterService {
    private static final Logger logger = LoggerFactory.getLogger(CsvWriterService.class);

    @Value("${csv.file.path:numbers.csv}")
    private String csvFilePath;

    public void writeNumbersToCsv(List<Integer> numbers) {
        File file = new File(csvFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.error("Error creating file '{}': {}", csvFilePath, e.getMessage());
            }
        }

        List<String[]> data = numbers.stream()
                .map(number -> new String[]{String.valueOf(number)})
                .collect(Collectors.toList());

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath, true))) {
            writer.writeAll(data);
        } catch (IOException e) {
            logger.error("Error writing numbers to CSV file '{}': {}", csvFilePath, e.getMessage(), e);
        }
    }
}
