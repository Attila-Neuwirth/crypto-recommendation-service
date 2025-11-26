package com.xm.crs.service;

import com.xm.crs.model.NormalizedRange;

import java.time.LocalDate;
import java.util.List;

/**
 * Calculates normalized values on different subsets of data
 */
public interface NormalizedRangeService {

    /**
     * Lists all stored currencies sorted by their normalized range in descending order
     * Normalized range = (maximum-minimum)/minimum
     * @return List of currencies ordered descending by their normalized range
     */
    List<NormalizedRange> getAllCurrenciesOrderedByNormalizedRange();

    /**
     * Returns the currency with the highest normalized range for a given day
     * @param date The day to query
     * @return The currency and the normalized range value
     */
    NormalizedRange getCurrencyWithHighestNormalizedRange(LocalDate date);
}
