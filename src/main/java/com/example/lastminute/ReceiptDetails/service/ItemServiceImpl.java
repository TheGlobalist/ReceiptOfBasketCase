package com.example.lastminute.ReceiptDetails.service;

import com.example.lastminute.ReceiptDetails.exceptions.IncorrectFormatException;
import com.example.lastminute.ReceiptDetails.model.Item;
import com.example.lastminute.ReceiptDetails.utility.TaxUtility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Service
public class ItemServiceImpl implements ItemService {

    private static final String CHART_ITEM_REGEX = "(\\d+) ([\\w\\s]* )at (\\d+.\\d+)";

    @Override
    public List<Item> generateItems(String cartItem) throws IncorrectFormatException {
        List<Item> toReturn = new ArrayList<>();
        Pattern pattern = Pattern.compile(CHART_ITEM_REGEX);
        Matcher matcher = pattern.matcher(cartItem);
        if (matcher.find()) {
            //If success, assume that the string has the correct format.
            //For the same concept, we're not catching any exceptions here as we are assuming that the string format is correct
            Integer quantity = Integer.parseInt(matcher.group(1));
            String productName = matcher.group(2);
            Boolean wasProductImported = productName.contains("imported");
            productName = wasProductImported ? productName.substring(9) : productName;
            //Same as above
            Double price = Double.parseDouble(matcher.group(3));
            for (int i = 0; i < quantity; i++) {
                Item toAdd = Item
                        .newBuilder()
                        .name(productName)
                        .price(price)
                        .hasProductBeenImported(wasProductImported)
                        .isProductTaxFree(TaxUtility.isProductTaxFree(productName))
                        .build();
                toReturn.add(toAdd);
            }

        } else {
            throw new IncorrectFormatException("Incorrect format for supplied string");
        }
        return toReturn;
    }

    @Override
    public String generateReceipt(List<Item> cartItems) {
        return null;
    }
}
