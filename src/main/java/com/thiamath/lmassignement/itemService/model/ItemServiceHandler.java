package com.thiamath.lmassignement.itemService.model;

/**
 * This Service is created as a false external microservice in charge of
 * manage items.
 */
public interface ItemServiceHandler {
    /**
     * I know that itemName is one of the worst IDs ever... but it's all we have for now.
     *
     * @param itemName The name of the item to retrieve.
     * @return A fully qualified {@link Item} from the database (wherever it is).
     */
    Item getItem(String itemName);
}
