package com.example.lastminute.ReceiptDetails.controllers;


import com.example.lastminute.ReceiptDetails.exceptions.IncorrectFormatException;
import com.example.lastminute.ReceiptDetails.model.Item;
import com.example.lastminute.ReceiptDetails.model.ShoppingCart;
import com.example.lastminute.ReceiptDetails.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {

    @Value("${basic.tax}")
    private Double basicTax;

    @Value("${import.tax}")
    private Double importTax;

    @Autowired
    private ItemService itemServiceImpl;

    @PostMapping(value="/produce")
    public String test(@RequestBody ShoppingCart cartFromInput) {
        List<Item> cartItems = new ArrayList<>();
        try {
            for (String cartItem : cartFromInput.getCart()) {
                cartItems.addAll(itemServiceImpl.generateItems(cartItem));
            }
        } catch (IncorrectFormatException ex) {
            return ex.getMessage();
        }
        return "ciao";
    }

}
