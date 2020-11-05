package com.example.lastminute.ReceiptDetails.model;

import org.springframework.beans.factory.annotation.Value;

public class BasicTax extends Tax {

    @Value("${basic.tax}")
    private Double basicTax;

    @Override
    public Double calculateTax(Item item) {
        return null;
    }
}
