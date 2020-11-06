package com.example.lastminute.ReceiptDetails.controllers;


import com.example.lastminute.ReceiptDetails.exceptions.IncorrectFormatException;
import com.example.lastminute.ReceiptDetails.model.Product;
import com.example.lastminute.ReceiptDetails.model.ResponseMessage;
import com.example.lastminute.ReceiptDetails.model.ShoppingCart;
import com.example.lastminute.ReceiptDetails.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {


    private final static Logger logger = LoggerFactory.getLogger(ReceiptController.class);

    @Autowired
    private ProductService productServiceImpl;

    @PostMapping(value="/produce")
    public ResponseEntity<ResponseMessage> test(@RequestBody ShoppingCart cartFromInput) {
        logger.info(">>>>>> /produce");
        ResponseEntity<ResponseMessage> toReturn = null;
        List<Product> cartItems = new ArrayList<>();
        logger.info("Parsing products");
        try {
            var iterator = cartFromInput.getCart().iterator();
            while (iterator.hasNext()) {
                String cartItem = iterator.next();
                cartItems.add(productServiceImpl.generateItem(cartItem));
            }
            logger.info("Parsing done");
        } catch (IncorrectFormatException ex) {
            logger.error(ex.getMessage());
            toReturn = new ResponseEntity<>(
                    ResponseMessage.newBuilder()
                            .statusCode(500)
                            .message("An error occurred!")
                            .data(ex.getMessage())
                            .build(), HttpStatus.INTERNAL_SERVER_ERROR
            );
            return toReturn;
        }
        logger.info("Generating receipt...");
        String receipt = productServiceImpl.generateReceipt(cartItems);
        logger.info("Done! Returning...");
        toReturn = new ResponseEntity<>(
                ResponseMessage.newBuilder()
                        .statusCode(200)
                        .message("Receipt successfully built!")
                        .data(receipt)
                        .build(), HttpStatus.OK
        );
        return toReturn;
    }

}
