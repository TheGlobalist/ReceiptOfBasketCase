package com.example.lastminute.ReceiptDetails.service;

import com.example.lastminute.ReceiptDetails.exceptions.IncorrectFormatException;
import com.example.lastminute.ReceiptDetails.model.Product;

import java.util.List;

public interface ProductService {

    Product generateItem(String cartItem) throws IncorrectFormatException;

    String generateReceipt(List<Product> cartItems);
}
