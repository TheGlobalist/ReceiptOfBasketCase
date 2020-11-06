package com.example.lastminute.ReceiptDetails.controllers;

import com.example.lastminute.ReceiptDetails.model.ShoppingCart;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptControllerTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testReceiptOne() {
        List<String> cart  = Arrays.asList("1 book at 12.49",
                "1 music CD at 14.99",
                "1 chocolate bar at 0.85"
        );
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCart(cart);
        try {
            mvc.perform(
                    post("/receipt/produce")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(shoppingCart))
            ).andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.data", is("1 book: 12.49\n1 music CD: 16.49\n1 chocolate bar: 0.85\nSales Taxes: 1.50\nTotal: 29.83")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReceiptTwo() {
        List<String> cart  = Arrays.asList("1 imported box of chocolates at 10.00",
                "1 imported bottle of perfume at 47.50"
        );

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCart(cart);
        try {
            mvc.perform(
                    post("/receipt/produce")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(shoppingCart))
            ).andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.data", is("1 imported box of chocolates: 10.50\n1 imported bottle of perfume: 54.65\nSales Taxes: 7.65\nTotal: 65.15")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReceiptThree() {
        List<String> cart  = Arrays.asList("1 imported bottle of perfume at 27.99",
                "1 bottle of perfume at 18.99",
                "1 packet of headache pills at 9.75",
                "1 imported box of chocolates at 11.25"
        );

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCart(cart);
        try {
            mvc.perform(
                    post("/receipt/produce")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(shoppingCart))
            ).andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.data", is("1 imported bottle of perfume: 32.19\n1 bottle of perfume: 20.89\n1 packet of headache pills: 9.75\n1 imported box of chocolates: 11.85\nSales Taxes: 6.70\nTotal: 74.68")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
