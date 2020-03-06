package com.thiamath.lmassignement.taxService;

import com.thiamath.lmassignement.itemService.model.Item;
import com.thiamath.lmassignement.itemService.model.ItemLabel;
import com.thiamath.lmassignement.itemService.model.ItemServiceHandler;
import com.thiamath.lmassignement.taxService.model.LineFormatException;
import com.thiamath.lmassignement.taxService.model.LineItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Manager that acts as a service to process inputs.
 * May be interfaced by a handler to put a rest layer for access.
 */
@Service
public class TaxServiceManager {

    private final Configuration configuration;
    private final ItemServiceHandler itemServiceHandler;

    private final List<ItemLabel> exemptedLabels = List.of(ItemLabel.BOOK, ItemLabel.FOOD, ItemLabel.MEDICINE);

    public TaxServiceManager(Configuration configuration, ItemServiceHandler itemServiceHandler) {
        this.configuration = configuration;
        this.itemServiceHandler = itemServiceHandler;
    }

    /**
     * Processes the input string into a tax awared output String.
     *
     * @param input The input to be processed.
     * @return A String with the output lines with processed taxes and totals.
     */
    public String process(String input) {
        // Even if I think that a common iteration will be more readable, I'll use Streams to be a little functional
        Supplier<Stream<LineItem>> lineItemStream = () -> Arrays.stream(input.split("\n"))
                .map(this::string2LineItem);
        BigDecimal total = lineItemStream.get().map(LineItem::getLineFinalPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        BigDecimal taxes = lineItemStream.get().map(
                lineItem -> lineItem.getItemTax()
                        .multiply(BigDecimal.valueOf(lineItem.getCount())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        List<String> output = new ArrayList<>();
        lineItemStream.get().forEach(lineItem -> output.add(printLineItem(lineItem)));
        output.add(String.format("Sales Taxes: %.2f", taxes));
        output.add(String.format("Total: %.2f", total));
        return String.join("\n", output);
    }

    private String printLineItem(LineItem lineItem) {
        if (lineItem.isImported()) {
            return String.format("%d %s %s: %.2f", lineItem.getCount(), "imported", lineItem.getItem().getTitle(), lineItem.getLineFinalPrice());
        }
        return String.format("%d %s: %.2f", lineItem.getCount(), lineItem.getItem().getTitle(), lineItem.getLineFinalPrice());
    }

    private LineItem string2LineItem(String line) {
        String[] words = line.trim().split(" ");

        int i = 0;
        int count = 0;
        boolean imported = false;
        BigDecimal itemPrice = BigDecimal.ZERO;
        String itemName = "";
        try {
            for (String word : words) {
                if (i == 0) {
                    count = Integer.parseInt(words[0]);
                } else if ("imported".equals(word)) {
                    imported = true;
                } else if (i == words.length - 1) {
                    itemPrice = new BigDecimal(word);
                } else if (!"at".equals(word)) {
                    itemName = String.join(" ", itemName, word);
                }
                i++;
            }
        } catch (NumberFormatException e) {
            throw new LineFormatException("Line bad formatted: " + line);
        }
        itemName = itemName.trim();
        Item item = itemServiceHandler.getItem(itemName);
        BigDecimal itemTax = calculateItemTax(item, itemPrice, imported);
        return new LineItem(
                item,
                count,
                imported,
                itemPrice,
                itemTax,
                itemPrice.add(itemTax).multiply(BigDecimal.valueOf(count))
        );
    }

    private BigDecimal calculateItemTax(Item item, BigDecimal itemPrice, boolean imported) {
        BigDecimal taxRate = getTaxRate(item, imported);
        return roundUpTo005(itemPrice.multiply(taxRate));
    }

    private BigDecimal roundUpTo005(BigDecimal itemTax) {
        double fmod = itemTax.doubleValue() % .05d;
        if (fmod > 0) {
            itemTax = itemTax.add(BigDecimal.valueOf(.05d - fmod));
        }
        return itemTax;
    }

    private BigDecimal getTaxRate(Item item, boolean imported) {
        BigDecimal taxRate = configuration.getBasicTax();
        if (isExempted(item)) {
            taxRate = BigDecimal.ZERO;
        }
        if (imported) {
            taxRate = taxRate.add(configuration.getImportedTax());
        }
        return taxRate;
    }

    private boolean isExempted(Item item) {
        if (item.getLabel() == null) {
            return false;
        }
        return exemptedLabels.contains(item.getLabel());
    }
}
