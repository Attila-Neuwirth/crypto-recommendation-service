package com.xm.crs.model;

/**
 * Summary data for a given currency
 * @param name Name of the currency (abbreviation)
 * @param oldest Oldest available price
 * @param newest Newest/most recent available price
 * @param minimum Minimum price available
 * @param maximum Maximum price available
 */
public record CryptoInfoSummary(String name, double oldest, double newest, double minimum, double maximum) {
}
