package com.xm.crs.exception;

import java.time.LocalDate;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(LocalDate date) {
        super(String.format("Data not found for date: %s", date));
    }
}
