package com.thiamath.lmassignement.taxService;

import com.thiamath.lmassignement.itemService.model.Item;
import com.thiamath.lmassignement.itemService.model.ItemLabel;
import com.thiamath.lmassignement.itemService.model.ItemServiceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Ecosystem {

    @Bean
    public ItemServiceHandler itemServiceHandler() {
        // Hardcoded database access
        return itemName -> {
            Item item;
            switch (itemName) {
                case "book":
                    item = new Item(itemName, ItemLabel.BOOK);
                    break;
                case "chocolate bar":
                case "box of chocolates":
                case "chocolates":
                    item = new Item(itemName, ItemLabel.FOOD);
                    break;
                case "packet of headache pills":
                    item = new Item(itemName, ItemLabel.MEDICINE);
                    break;
                case "music CD":
                case "bottle of perfume":
                default:
                    item = new Item(itemName, null);
                    break;
            }
            return item;
        };
    }
}
