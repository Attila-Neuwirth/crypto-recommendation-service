package com.xm.crs.service;

import com.xm.crs.model.CryptoInfoSummary;

/**
 * Provides general information about currencies
 */
public interface CryptoInfoService {

    /**
     * Returns summary information about given currency
     * @param name The currency to query
     * @return CryptoInfoSummary object with currency name, minimum, maximum, oldest and newest values
     */
    CryptoInfoSummary getCryptoInfo(String name);
}
