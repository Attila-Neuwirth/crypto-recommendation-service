package com.xm.crs.persistence.impl;

import com.xm.crs.exception.CurrencyNotFoundException;
import com.xm.crs.model.CryptoPrice;
import com.xm.crs.model.TimestampedPrice;
import com.xm.crs.persistence.CryptoPriceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Stores prices with timestamp for currencies.
 * Separate storage is created for every currency on demand
 */
@Component
public class InMemoryCryptoPriceRepository implements CryptoPriceRepository {

    private final HashMap<String, CryptoPriceStorage> cryptoStorage = new HashMap<>();

    /**
     * Adds a price to storage.
     * Stores currencies in their respective store. These are created on demand
     *
     * @param price The currency with timestamp and price to be added
     */
    @Override
    public void put(CryptoPrice price) {
        CryptoPriceStorage storage;
        if (!cryptoStorage.containsKey(price.cryptoName())) {
            storage = new CryptoPriceStorage();
            cryptoStorage.put(price.cryptoName(), storage);
        } else {
            storage = cryptoStorage.get(price.cryptoName());
        }

        storage.add(price.timestampedPrice());
    }

    /**
     * Lists all currencies which have stored information
     * @return Set of currency abbreviations stored in the repository
     */
    public Set<String> getAvailableCurrencies() {
        return cryptoStorage.keySet();
    }

    /**
     * Returns the minimum stored price for the given currency
     * @param currency The currency to be queried
     * @return The minimum stored price
     */
    @Override
    public Double getMinimum(String currency) {
        return getCurrencyOrThrow(currency).getMinPrice();
    }

    /**
     * Returns the maximum stored price for the given currency
     * @param currency The currency to be queried
     * @return The maximum stored price
     */
    @Override
    public Double getMaximum(String currency) {
        return getCurrencyOrThrow(currency).getMaxPrice();
    }

    /**
     * Returns the oldest stored price for the given currency
     * @param currency The currency to be queried
     * @return The oldest stored price
     */
    @Override
    public Double getOldest(String currency) {
        return getCurrencyOrThrow(currency).getOldestPrice();
    }

    /**
     * Returns the newest stored price for the given currency
     * @param currency The currency to be queried
     * @return The newest stored price
     */
    @Override
    public Double getNewest(String currency) {
        return getCurrencyOrThrow(currency).getNewestPrice();
    }

    /**
     * Retrieves all stored prices for a given currency on a given date
     */
    @Override
    public List<CryptoPrice> getValuesForDate(String currency, LocalDate date) {
        List<CryptoPrice> result = new LinkedList<>();
        for (TimestampedPrice value : cryptoStorage.get(currency).getValuesForDay(date)) {
            result.add(new CryptoPrice(currency, value));
        }
        return result;
    }

    private CryptoPriceStorage getCurrencyOrThrow(String key) {
        if (cryptoStorage.containsKey(key)) {
            return cryptoStorage.get(key);
        } else {
            throw new CurrencyNotFoundException(key);
        }
    }
}
