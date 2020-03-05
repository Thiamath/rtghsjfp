package com.thiamath.lmassignement.taxService;

import org.springframework.stereotype.Service;

@Service
public class TaxServiceManager {

    private final Configuration configuration;

    public TaxServiceManager(Configuration configuration) {
        this.configuration = configuration;
    }

    public String process(String input) {
        return "nul";
    }
}
