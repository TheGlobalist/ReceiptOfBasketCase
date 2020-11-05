package com.example.lastminute.ReceiptDetails.model;

import lombok.Builder;
import lombok.Getter;

@Builder(builderMethodName = "newBuilder")
@Getter
public class Item {

    private String name;
    private Double price;
    private Boolean isProductTaxFree;
    private Boolean hasProductBeenImported;



}
