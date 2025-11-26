package com.xm.crs.service.impl;

import com.xm.crs.model.CryptoPrice;
import com.xm.crs.model.TimestampedPrice;
import com.xm.crs.persistence.CryptoPriceRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Service to load stored prices on application startup
 * Runs after application is ready
 */
@Component
public class PriceLoaderService {

    private static final String EXPECTED_HEADER = "timestamp,symbol,price";
    private final CryptoPriceRepository repository;

    public PriceLoaderService(CryptoPriceRepository repository) {
        this.repository = repository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadPrices() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] cryptoValues = resolver.getResources("prices/*_values.csv");
        for (Resource resource : cryptoValues) {
            loadCryptoPrices(resource);
        }
    }

    private void loadCryptoPrices(Resource resource) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        String header = reader.readLine();

        if (!EXPECTED_HEADER.equals(header)) {
            throw new IllegalArgumentException("header mismatch");
        }
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");

            repository.put(new CryptoPrice(values[1], new TimestampedPrice(Long.parseLong(values[0]), Double.parseDouble(values[2]))));
        }
    }
}
