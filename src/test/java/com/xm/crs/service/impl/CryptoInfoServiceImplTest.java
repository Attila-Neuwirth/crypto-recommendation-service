package com.xm.crs.service.impl;

import com.xm.crs.exception.CurrencyNotFoundException;
import com.xm.crs.model.CryptoInfoSummary;
import com.xm.crs.persistence.CryptoPriceRepository;
import com.xm.crs.service.CryptoInfoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CryptoInfoServiceImplTest {

    private static final String CURRENCY = "BTC";
    private static final String UNKNOWN_CURRENCY = "unknown";
    private static final double MINIMUM_VALUE = 1.0;
    private static final double OLDEST_VALUE = 3.0;
    private static final double NEWEST_VALUE = 7.0;
    private static final double MAXIMUM_VALUE = 9.0;

    private static final CryptoInfoSummary EXPECTED_CRYPTO_INFO =
            new CryptoInfoSummary(CURRENCY, OLDEST_VALUE, NEWEST_VALUE, MINIMUM_VALUE, MAXIMUM_VALUE);

    private final CryptoPriceRepository repository = mock();
    private final CryptoInfoService service = new CryptoInfoServiceImpl(repository);

    @Test
    void whenCurrencyExistsGetCryptoInfoShouldReturnInformation() {
        when(repository.getMinimum(CURRENCY)).thenReturn(MINIMUM_VALUE);
        when(repository.getMaximum(CURRENCY)).thenReturn(MAXIMUM_VALUE);
        when(repository.getOldest(CURRENCY)).thenReturn(OLDEST_VALUE);
        when(repository.getNewest(CURRENCY)).thenReturn(NEWEST_VALUE);

        CryptoInfoSummary result = service.getCryptoInfo(CURRENCY);

        assertEquals(EXPECTED_CRYPTO_INFO, result);
    }

    @Test
    void whenRepositoryThrowsExceptionThenItShouldBePropagated() {
        when(repository.getMinimum(UNKNOWN_CURRENCY)).thenThrow(new CurrencyNotFoundException(UNKNOWN_CURRENCY));

        assertThrows(
                CurrencyNotFoundException.class,
                () ->service.getCryptoInfo(UNKNOWN_CURRENCY),
                String.format("Currency not found: %s", UNKNOWN_CURRENCY));
    }
}