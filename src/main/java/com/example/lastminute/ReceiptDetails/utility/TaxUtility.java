package com.example.lastminute.ReceiptDetails.utility;

import com.example.lastminute.ReceiptDetails.model.Product;

import java.util.stream.Stream;

public class TaxUtility {

    public static boolean isProductTaxFree(Product extractedProduct) {
        return Stream.of("pills", "chocolate", "book").anyMatch(exemptionCategory -> extractedProduct.getName().contains(exemptionCategory));
    }

    public static Double calculateStandardTax(Product product, Double taxPercentage) {
        return product.getQuantity() * (product.getPrice() * taxPercentage);
    }

    public static Double calculateImportTax(Product product, Double taxPercentage) {
        return product.getQuantity() * (Math.ceil(
           (product.getPrice() * taxPercentage) * 20
        )/ 20);
    }
}
