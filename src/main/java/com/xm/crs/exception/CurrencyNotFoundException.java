package com.xm.crs.exception;

public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(String currency) {
        super(String.format("Currency not found: %s", currency));
    }
}
