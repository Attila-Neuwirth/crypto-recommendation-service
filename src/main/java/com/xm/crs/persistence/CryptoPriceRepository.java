package com.xm.crs.persistence;

import com.xm.crs.model.CryptoPrice;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Repository to store currencies with their rates in time
 * Allows to query summary information (minimum, maximum, oldest and newest values)
 */
public interface CryptoPriceRepository {

    void put(CryptoPrice value);

    Set<String> getAvailableCurrencies();

    Double getMinimum(String currency);

    Double getMaximum(String currency);

    Double getOldest(String name);

    Double getNewest(String name);

    List<CryptoPrice> getValuesForDate(String currency, LocalDate date);
}
