package com.xm.crs.controller;

import com.xm.crs.model.CryptoInfoSummary;
import com.xm.crs.service.CryptoInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class to provide summary information about currencies
 */
@RestController
@RequestMapping("crypto-info")
public class CryptoInfoController {

    private final CryptoInfoService service;

    public CryptoInfoController(CryptoInfoService service) {
        this.service = service;
    }

    /**
     * @param currency Abbreviation of the currency we would like to have summary about
     * @return Summary with currency name, minimum, maximum, oldest and newest prices
     */
    @GetMapping("/{currency}")
    public CryptoInfoSummary getInfo(@PathVariable String currency) {
        return service.getCryptoInfo(currency);
    }
}
