package com.example.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Service that generates random numbers and sends them to a consumer service.
 * It also writes generated numbers to a CSV file.
 */
@Service
public class NumberGeneratorService {
    private static final Logger logger = LoggerFactory.getLogger(NumberGeneratorService.class);

    /**
     * The maximum number of elements the queue can hold.
     */
    private static final int MAX_STREAM_SIZE = 100;

    /**
     * The size of each batch of numbers to generate.
     */
    private static final int BATCH_SIZE = 5;

    /**
     * A thread-safe queue that holds generated numbers.
     */
    private final BlockingQueue<Integer> numberQueue = new LinkedBlockingQueue<>(MAX_STREAM_SIZE);
    private final Random random = new Random();
    private final RestTemplate restTemplate;
    private final CsvWriterService csvWriterService;

    @Value("${consumer.url}")
    private String consumerUrl;

    public NumberGeneratorService(RestTemplate restTemplate, CsvWriterService csvWriterService) {
        this.restTemplate = restTemplate;
        this.csvWriterService = csvWriterService;
    }

    /**
     * Scheduled task that generates a batch of random numbers and adds them to the queue.
     * The task runs every second.
     */
    @Scheduled(fixedRate = 1000)
    public void generateNumbers() {
        logger.info("Scheduled task started.");
        logger.info("Queue size before generating number: {}", numberQueue.size());

        List<Integer> batch = IntStream.range(0, BATCH_SIZE)
                .map(i -> random.nextInt(1000))
                .boxed()
                .collect(Collectors.toList());

        int spaceAvailable = MAX_STREAM_SIZE - numberQueue.size();
        if (spaceAvailable > 0) {
            // Trim batch if necessary
            if (batch.size() > spaceAvailable) {
                batch = batch.subList(0, spaceAvailable);
            }
            numberQueue.addAll(batch);
            csvWriterService.writeNumbersToCsv(batch);
        } else {
            logger.warn("Queue is full. Unable to add new numbers.");
        }

        logger.info("Generated batch of numbers: {}", batch);
        logger.info("Queue size after generating numbers: {}", numberQueue.size());
        logger.info("Scheduled task finished.");
    }

    /**
     * Scheduled task that processes the numbers in the queue and sends them to the consumer service.
     * The task runs every 5 seconds.
     */
    @Scheduled(fixedRate = 5000)
    public void processQueue() throws InterruptedException {
        List<Integer> batch = numberQueue.stream()
                .limit(BATCH_SIZE)
                .collect(Collectors.toList());

        if (!batch.isEmpty()) {
            logger.info("Processing batch: {}", batch);

            // Remove processed numbers from the queue
            numberQueue.removeAll(batch);

            for (int i = 0; i < 5; i++) {
                try {
                    restTemplate.postForObject(consumerUrl, batch, String.class);
                    logger.info("Batch of numbers sent successfully.");
                    break;
                } catch (ResourceAccessException e) {
                    logger.error("Retrying connection to consumer service...");
                    Thread.sleep(5000);
                }
            }
        }
    }
}
