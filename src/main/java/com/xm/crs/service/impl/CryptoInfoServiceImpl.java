package com.xm.crs.service.impl;

import com.xm.crs.model.CryptoInfoSummary;
import com.xm.crs.persistence.CryptoPriceRepository;
import com.xm.crs.service.CryptoInfoService;
import org.springframework.stereotype.Component;

@Component
public class CryptoInfoServiceImpl implements CryptoInfoService {
    private final CryptoPriceRepository repository;

    public CryptoInfoServiceImpl(CryptoPriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public CryptoInfoSummary getCryptoInfo(String name) {
        return new CryptoInfoSummary(
                name,
                repository.getOldest(name),
                repository.getNewest(name),
                repository.getMinimum(name),
                repository.getMaximum(name)
        );
    }
}
