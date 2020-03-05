package com.thiamath.lmassignement.taxService.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LineItem {
    private final Item item;
    private final int count;
    private final BigDecimal itemPrice;
}
