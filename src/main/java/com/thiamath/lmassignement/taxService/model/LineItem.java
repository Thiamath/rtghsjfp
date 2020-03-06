package com.thiamath.lmassignement.taxService.model;

import com.thiamath.lmassignement.itemService.model.Item;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LineItem {
    private final Item item;
    private final int count;
    private final boolean imported;
    private final BigDecimal itemPrice;
    private final BigDecimal itemTax;
    private final BigDecimal lineFinalPrice;
}
