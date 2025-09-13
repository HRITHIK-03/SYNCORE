package com.example.syncore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MicroserviceSimulator {
    private static final Logger log = LoggerFactory.getLogger(MicroserviceSimulator.class);
    private final Random random = new Random();

    public String callAnotherService(String input) {
        log.info("Simulating internal microservice call with input={}", input);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}
        if (random.nextInt(10) < 2) { // 20% chance of error
            log.error("Internal service call failed for input={}", input);
            throw new RuntimeException("Internal service unavailable");
        }
        return "Processed:" + input;
    }
}
