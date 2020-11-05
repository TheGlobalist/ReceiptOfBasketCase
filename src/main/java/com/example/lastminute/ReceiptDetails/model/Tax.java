package com.example.lastminute.ReceiptDetails.model;

import java.util.stream.Stream;

public abstract class Tax {

    public static boolean checkIfItemIsTaxFree(String extractedProduct) {
        return Stream.of("pills","chocolate","book").anyMatch(exemptionCategory -> extractedProduct.contains(exemptionCategory));
    }

    public abstract Double calculateTax(Item item);

}
