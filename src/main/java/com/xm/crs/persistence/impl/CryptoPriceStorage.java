package com.xm.crs.persistence.impl;

import com.xm.crs.exception.DataNotFoundException;
import com.xm.crs.model.TimestampedPrice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import static java.util.Objects.requireNonNull;

/**
 * Stores price information with timestamps for a currency.
 * Entries are partitioned internally by date for quicker access
 * Oldest, newest, minimum and maximum values are calculated and updated as entries are added
 */
public class CryptoPriceStorage {

    private Double minValue;
    private Double maxValue;
    private TimestampedPrice oldest;
    private TimestampedPrice newest;

    private final TreeMap<LocalDate, List<TimestampedPrice>> cryptoPricesByDate;

    public CryptoPriceStorage() {
        this.cryptoPricesByDate = new TreeMap<>();
    }

    public Double getMinPrice() {
        return minValue;
    }

    public Double getMaxPrice() {
        return maxValue;
    }

    public Double getOldestPrice() {
        return oldest.price();
    }

    public Double getNewestPrice() {
        return newest.price();
    }

    /**
     * Returns all stored prices with timestamps for a given day
     * @param date The date to query data from
     * @return List of timestamped prices for the given day
     */
    public List<TimestampedPrice> getValuesForDay(LocalDate date) {
        List<TimestampedPrice> result;
        if (cryptoPricesByDate.containsKey(date)) {
            result = new ArrayList<>(cryptoPricesByDate.get(date));
        } else {
            throw new DataNotFoundException(date);
        }

        return result;
    }


    /**
     * Adds a new (not null) price with timestamp to the store
     * @param price The timestamped price to be added
     */
    public void add(TimestampedPrice price) {
        requireNonNull(price);

        adjustPriceInformation(price);

        List<TimestampedPrice> prices;
        if (cryptoPricesByDate.containsKey(price.date())) {
            prices = cryptoPricesByDate.get(price.date());
        } else {
            prices = new LinkedList<>();
            cryptoPricesByDate.put(price.date(), prices);
        }
        prices.add(price);
    }

    /**
     * Updates summary information when a new item is added to the store
     * @param price The new price to check
     */
    private void adjustPriceInformation(TimestampedPrice price) {
        if (minValue == null || price.price() < minValue) {
            minValue = price.price();
        }
        if (maxValue == null || price.price() > maxValue) {
            maxValue = price.price();
        }
        if (oldest == null || price.timestamp() < oldest.timestamp()) {
            oldest = price;
        }
        if (newest == null || price.timestamp() > newest.timestamp()) {
            newest = price;
        }
    }
}
