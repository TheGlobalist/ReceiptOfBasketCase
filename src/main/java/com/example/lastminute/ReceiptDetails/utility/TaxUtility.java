package com.example.lastminute.ReceiptDetails.utility;

import com.example.lastminute.ReceiptDetails.model.Item;

import java.util.stream.Stream;

public class TaxUtility {


    public static boolean isProductTaxFree(String extractedProduct) {
        return Stream.of("pills", "chocolate", "book").anyMatch(exemptionCategory -> extractedProduct.contains(exemptionCategory));
    }
}
