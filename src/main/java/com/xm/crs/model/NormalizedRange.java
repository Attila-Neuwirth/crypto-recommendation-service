package com.xm.crs.model;

/**
 * Data holder for normalized range value of a currency
 * @param currency Currency name
 * @param range Normalized range value
 */
public record NormalizedRange(String currency, double range) {
}
