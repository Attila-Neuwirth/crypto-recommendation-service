package com.xm.crs.service.impl;

import com.xm.crs.model.CryptoPrice;
import com.xm.crs.model.NormalizedRange;
import com.xm.crs.model.TimestampedPrice;
import com.xm.crs.persistence.CryptoPriceRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NormalizedRangeServiceImplTest {

    public static final String CURRENCY1 = "CUR1";
    public static final String CURRENCY2 = "CUR2";
    public static final String CURRENCY3 = "CUR3";
    private static final Set<String> AVAILABLE_CURRENCIES = Set.of(CURRENCY1, CURRENCY2, CURRENCY3);

    private static final double CURRENCY1_MINIMUM = 1.0;
    private static final double CURRENCY1_MAXIMUM = 2.0;
    private static final double CURRENCY2_MINIMUM = 1.0;
    private static final double CURRENCY2_MAXIMUM = 3.0;
    private static final double CURRENCY3_MINIMUM = 1.5;
    private static final double CURRENCY3_MAXIMUM = 2.0;

    private static final List<CryptoPrice> CURRENCY1_ENTRIES =
            List.of(
                    new CryptoPrice(CURRENCY1, new TimestampedPrice(1, CURRENCY1_MAXIMUM)),
                    new CryptoPrice(CURRENCY1, new TimestampedPrice(2, CURRENCY1_MINIMUM))
            );
    private static final List<CryptoPrice> CURRENCY2_ENTRIES =
            List.of(
                    new CryptoPrice(CURRENCY2, new TimestampedPrice(1, CURRENCY2_MINIMUM)),
                    new CryptoPrice(CURRENCY2, new TimestampedPrice(2, CURRENCY2_MAXIMUM))
            );
    private static final List<CryptoPrice> CURRENCY3_ENTRIES =
            List.of(
                    new CryptoPrice(CURRENCY3, new TimestampedPrice(1, CURRENCY3_MAXIMUM)),
                    new CryptoPrice(CURRENCY3, new TimestampedPrice(2, CURRENCY3_MINIMUM))
            );
    private static final LocalDate DATE = LocalDate.now();

    private static final List<NormalizedRange> EXPECTED_NORMALIZED_RANGE_LIST =
            List.of(new NormalizedRange(CURRENCY2, 2.0),
                    new NormalizedRange(CURRENCY1, 1.0),
                    new NormalizedRange(CURRENCY3, 1.0/3)
            );

    private static final NormalizedRange EXPECTED_MAX_NORMALIZED_RANGE = new NormalizedRange(CURRENCY2, 2.0);

    private final CryptoPriceRepository repository = mock();
    private final NormalizedRangeServiceImpl service = new NormalizedRangeServiceImpl(repository);

    @Test
    void getAllCurrenciesOrderedByNormalizedRange() {
        when(repository.getAvailableCurrencies()).thenReturn(AVAILABLE_CURRENCIES);
        when(repository.getMinimum(CURRENCY1)).thenReturn(CURRENCY1_MINIMUM);
        when(repository.getMaximum(CURRENCY1)).thenReturn(CURRENCY1_MAXIMUM);
        when(repository.getMinimum(CURRENCY2)).thenReturn(CURRENCY2_MINIMUM);
        when(repository.getMaximum(CURRENCY2)).thenReturn(CURRENCY2_MAXIMUM);
        when(repository.getMinimum(CURRENCY3)).thenReturn(CURRENCY3_MINIMUM);
        when(repository.getMaximum(CURRENCY3)).thenReturn(CURRENCY3_MAXIMUM);

        List<NormalizedRange> result = service.getAllCurrenciesOrderedByNormalizedRange();

        assertEquals(EXPECTED_NORMALIZED_RANGE_LIST, result);
    }

    @Test
    void getCurrencyWithHighestNormalizedRange() {
        when(repository.getAvailableCurrencies()).thenReturn(AVAILABLE_CURRENCIES);
        when(repository.getValuesForDate(CURRENCY1, DATE)).thenReturn(CURRENCY1_ENTRIES);
        when(repository.getValuesForDate(CURRENCY2, DATE)).thenReturn(CURRENCY2_ENTRIES);
        when(repository.getValuesForDate(CURRENCY3, DATE)).thenReturn(CURRENCY3_ENTRIES);

        NormalizedRange result = service.getCurrencyWithHighestNormalizedRange(DATE);

        assertEquals(EXPECTED_MAX_NORMALIZED_RANGE, result);
    }
}