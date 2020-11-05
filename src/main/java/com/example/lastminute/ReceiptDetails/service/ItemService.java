package com.example.lastminute.ReceiptDetails.service;

import com.example.lastminute.ReceiptDetails.exceptions.IncorrectFormatException;
import com.example.lastminute.ReceiptDetails.model.Item;

import java.util.List;

public interface ItemService {

    List<Item> generateItems(String cartItem) throws IncorrectFormatException;

    String generateReceipt(List<Item> cartItems);
}
