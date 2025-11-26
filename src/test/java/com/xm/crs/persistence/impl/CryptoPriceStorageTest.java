package com.xm.crs.persistence.impl;

import com.xm.crs.model.TimestampedPrice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CryptoPriceStorageTest {

    private static final String CURRENCY_NAME = "currency";
    public static final int TIMESTAMP1 = 1;
    public static final int OLDEST_PRICE = 21;
    public static final int TIMESTAMP2 = 10;
    public static final int MAXIMUM_PRICE = 42;
    public static final int TIMESTAMP3 = 20;
    public static final int MINIMUM_PRICE = 11;
    public static final int TIMESTAMP4 = 30;
    public static final int NEWEST_PRICE = 31;

    private static final TimestampedPrice TIMESTAMPED_PRICE1 = new TimestampedPrice(TIMESTAMP1, OLDEST_PRICE);
    private static final TimestampedPrice TIMESTAMPED_PRICE2 = new TimestampedPrice(TIMESTAMP2, MAXIMUM_PRICE);
    private static final TimestampedPrice TIMESTAMPED_PRICE3 = new TimestampedPrice(TIMESTAMP3, MINIMUM_PRICE);
    private static final TimestampedPrice TIMESTAMPED_PRICE4 = new TimestampedPrice(TIMESTAMP4, NEWEST_PRICE);

    @Test
    void whenPricesAreAddedThenMinimumPriceInformationIsUpdated() {
        CryptoPriceStorage storage = createStorageWithTestData();

        double result = storage.getMinPrice();

        assertEquals(MINIMUM_PRICE, result);
    }
    @Test
    void whenPricesAreAddedThenMaximumPriceInformationIsUpdated() {
        CryptoPriceStorage storage = createStorageWithTestData();

        double result = storage.getMaxPrice();

        assertEquals(MAXIMUM_PRICE, result);
    }
    @Test
    void whenPricesAreAddedThenOldestPriceInformationIsUpdated() {
        CryptoPriceStorage storage = createStorageWithTestData();

        double result = storage.getOldestPrice();

        assertEquals(OLDEST_PRICE, result);
    }
    @Test
    void whenPricesAreAddedThenNewestPriceInformationIsUpdated() {
        CryptoPriceStorage storage = createStorageWithTestData();

        double result = storage.getNewestPrice();

        assertEquals(NEWEST_PRICE, result);
    }
    private CryptoPriceStorage createStorageWithTestData() {
        CryptoPriceStorage storage = new CryptoPriceStorage();
        storage.add(TIMESTAMPED_PRICE1);
        storage.add(TIMESTAMPED_PRICE2);
        storage.add(TIMESTAMPED_PRICE3);
        storage.add(TIMESTAMPED_PRICE4);
        return storage;
    }
}