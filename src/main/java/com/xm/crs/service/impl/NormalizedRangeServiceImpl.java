package com.xm.crs.service.impl;

import com.xm.crs.model.CryptoPrice;
import com.xm.crs.model.NormalizedRange;
import com.xm.crs.persistence.CryptoPriceRepository;
import com.xm.crs.service.NormalizedRangeService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

@Component
public class NormalizedRangeServiceImpl implements NormalizedRangeService {
    private final CryptoPriceRepository repository;

    public NormalizedRangeServiceImpl(CryptoPriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<NormalizedRange> getAllCurrenciesOrderedByNormalizedRange() {
        TreeSet<NormalizedRange> result = new TreeSet<>(Comparator.comparingDouble(NormalizedRange::range).reversed());
        for (String currency : repository.getAvailableCurrencies()) {
            double minimum = repository.getMinimum(currency);
            double maximum = repository.getMaximum(currency);
            result.add(new NormalizedRange(currency, (maximum-minimum)/minimum));
        }
        return new ArrayList<>(result);
    }

    @Override
    public NormalizedRange getCurrencyWithHighestNormalizedRange(LocalDate date) {
        Double maxNormalizedRange = null;
        String maxCurrency = null;

        for (String currency : repository.getAvailableCurrencies()) {
            List<CryptoPrice> dailyValues = repository.getValuesForDate(currency, date);
            double normalizedRange = getNormalizedRange(dailyValues);
            if (maxNormalizedRange == null || normalizedRange > maxNormalizedRange) {
                maxNormalizedRange = normalizedRange;
                maxCurrency = currency;
            }
        }
        return new NormalizedRange(maxCurrency, maxNormalizedRange);
    }

    private static double getNormalizedRange(List<CryptoPrice> dailyValues) {
        Double minimum = null;
        Double maximum = null;
        for (CryptoPrice value : dailyValues) {
            if (minimum == null || value.timestampedPrice().price() < minimum) {
                minimum = value.timestampedPrice().price();
            }
            if (maximum == null || value.timestampedPrice().price() > maximum) {
                maximum = value.timestampedPrice().price();
            }
        }
        return  (maximum-minimum)/minimum;
    }
}
