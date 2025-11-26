package com.xm.crs.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Represents a price value in time
 * @param timestamp Timestamp of the price in Epoch milliseconds
 * @param price Price value
 */
public record TimestampedPrice(long timestamp, double price) {
    public LocalDate date() {
        return LocalDate.from(Instant.ofEpochMilli(timestamp).atZone(ZoneId.of("UTC")));
    }
}
