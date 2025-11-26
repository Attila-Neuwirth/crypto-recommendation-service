package com.xm.crs.controller.errorhandling;

import com.xm.crs.exception.CurrencyNotFoundException;
import com.xm.crs.exception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({CurrencyNotFoundException.class, DataNotFoundException.class})
    public ProblemDetail handleCurrencyNotFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage());
    }

}
