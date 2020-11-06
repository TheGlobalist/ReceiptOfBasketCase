package com.example.lastminute.ReceiptDetails.model;

import lombok.Builder;
import lombok.Getter;


@Builder(builderMethodName = "newBuilder")
@Getter
public class Product {

    private String name;
    private Double price;
    private Integer quantity;
    private Boolean isProductImported;

}
