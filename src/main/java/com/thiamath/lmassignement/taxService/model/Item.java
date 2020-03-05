package com.thiamath.lmassignement.taxService.model;

import lombok.Data;

@Data
public class Item {
    private final String title;
    private final ItemLabel label;
    private final boolean imported;
}
