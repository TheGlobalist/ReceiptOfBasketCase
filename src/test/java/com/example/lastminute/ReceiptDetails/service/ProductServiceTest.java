package com.example.lastminute.ReceiptDetails.service;

import com.example.lastminute.ReceiptDetails.exceptions.IncorrectFormatException;
import com.example.lastminute.ReceiptDetails.model.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductService productServiceImpl;

    @Test
    public void generateItemNotImported() {
        Product toCheck = null;
        try {
            toCheck = this.productServiceImpl.generateItem("1 book at 12.49");
        } catch (IncorrectFormatException ex) {
            Assert.fail(ex.getMessage());
        }
        assertEquals(toCheck.getPrice(),12.49);
        assertEquals(toCheck.getQuantity(),1 );
        assertEquals(toCheck.getIsProductImported(), false);
    }

    @Test
    public void generateItemImported() {
        Product toCheck = null;
        try {
            toCheck = this.productServiceImpl.generateItem("1 imported bottle of perfume at 27.99");
        } catch (IncorrectFormatException ex) {
            Assert.fail(ex.getMessage());
        }
        assertEquals(toCheck.getName(),"imported bottle of perfume");
        assertEquals(toCheck.getPrice(),27.99);
        assertEquals(toCheck.getQuantity(),1 );
        assertEquals(toCheck.getIsProductImported(), true);;
    }

    @Test
    public void generateReceipt() {
        List<Product> cart = Arrays.asList(
                Product.newBuilder()
                        .name("imported box of chocolates")
                        .price(10.00)
                        .quantity(1)
                        .isProductImported(true)
                        .build(),
                Product.newBuilder()
                        .name("imported bottle of perfume")
                        .price(47.50)
                        .quantity(1)
                        .isProductImported(true)
                        .build()
        );
        String receipt = this.productServiceImpl.generateReceipt(cart);
        assertEquals(receipt, "1 imported box of chocolates: 10.50\n" +
                "1 imported bottle of perfume: 54.65\n" +
                "Sales Taxes: 7.65\n" +
                "Total: 65.15");
    }
}