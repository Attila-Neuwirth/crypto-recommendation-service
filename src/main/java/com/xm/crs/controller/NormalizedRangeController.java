package com.xm.crs.controller;

import com.xm.crs.model.NormalizedRange;
import com.xm.crs.service.NormalizedRangeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class to provide information about normalized ranges for currencies
 */
@RestController
@RequestMapping("normalized-range")
public class NormalizedRangeController {
    private final NormalizedRangeService service;

    public NormalizedRangeController(NormalizedRangeService service) {
        this.service = service;
    }

    /**
     * Endpoint to return all currencies sorted by normalized range in reverse/descending order
     * @return List of currencies sorted descending by normalized range values
     */
    @GetMapping("/all")
    public List<NormalizedRange> getAllCurrenciesSorted() {
        return service.getAllCurrenciesOrderedByNormalizedRange();
    }

    /**
     * Endpoint to return the currency with the maximum normalized range for a given date
     * @param date Local date (e.g. 2022-01-07)
     * @return Object containing the currency name and the max normalized rage value
     */
    @GetMapping("/daily/{date}")
    public NormalizedRange getMaxNormalizedRangeForDate(@PathVariable LocalDate date) {
        return service.getCurrencyWithHighestNormalizedRange(date);
    }
}
