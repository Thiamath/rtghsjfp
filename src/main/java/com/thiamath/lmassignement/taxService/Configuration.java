package com.thiamath.lmassignement.taxService;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
@ConfigurationProperties(prefix = "tax-service")
public class Configuration {
    private BigDecimal basicTax;
    private BigDecimal importedTax;
}

