package com.example.lastminute.ReceiptDetails.controllers;


import com.example.lastminute.ReceiptDetails.exceptions.IncorrectFormatException;
import com.example.lastminute.ReceiptDetails.model.Product;
import com.example.lastminute.ReceiptDetails.model.ResponseMessage;
import com.example.lastminute.ReceiptDetails.model.ShoppingCart;
import com.example.lastminute.ReceiptDetails.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {


    @Autowired
    private ProductService productServiceImpl;

    @PostMapping(value="/produce")
    public ResponseMessage test(@RequestBody ShoppingCart cartFromInput) {
        List<Product> cartItems = new ArrayList<>();
        try {
            var iterator = cartFromInput.getCart().iterator();
            while (iterator.hasNext()) {
                String cartItem = iterator.next();
                cartItems.add(productServiceImpl.generateItem(cartItem));
            }
        } catch (IncorrectFormatException ex) {
            return ResponseMessage.newBuilder()
                    .statusCode(500)
                    .message("An error occurred!")
                    .data(ex.getMessage())
                    .build();
        }
        String receipt = productServiceImpl.generateReceipt(cartItems);
        return ResponseMessage.newBuilder()
                .statusCode(200)
                .message("Receipt successfully built!")
                .data(receipt)
                .build();
    }

}
