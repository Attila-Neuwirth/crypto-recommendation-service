package com.xm.crs.model;


/**
 * Timestamped price data for a given currency
 * @param cryptoName
 * @param timestampedPrice Contains price with a timestamp. See: {@link TimestampedPrice}
 */
public record CryptoPrice(String cryptoName, TimestampedPrice timestampedPrice) {
}
