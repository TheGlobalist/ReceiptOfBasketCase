package com.example.lastminute.ReceiptDetails.service;

import com.example.lastminute.ReceiptDetails.exceptions.IncorrectFormatException;
import com.example.lastminute.ReceiptDetails.model.Product;
import com.example.lastminute.ReceiptDetails.utility.TaxUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String CHART_ITEM_REGEX = "(\\d+) ([\\w\\s]* )at (\\d+.\\d+)";

    @Value("${basic.tax}")
    private Double basicTax;

    @Value("${import.tax}")
    private Double importTaxVal;

    @Override
    public Product generateItem(String cartItem) throws IncorrectFormatException {
        Product toReturn = null;
        Pattern pattern = Pattern.compile(CHART_ITEM_REGEX);
        Matcher matcher = pattern.matcher(cartItem);
        if (matcher.find()) {
            //If success, assume that the string has the correct format.
            //For the same concept, we're not catching any exceptions here as we are assuming that the string format is correct
            Integer quantity = Integer.parseInt(matcher.group(1).strip());
            String productName = matcher.group(2).strip();
            Boolean wasProductImported = productName.contains("imported");
            //Same as above
            Double price = Double.parseDouble(matcher.group(3).strip());
            toReturn = Product
                    .newBuilder()
                    .name(productName)
                    .price(price)
                    .quantity(quantity)
                    .isProductImported(wasProductImported)
                    .build();


        } else {
            throw new IncorrectFormatException("Incorrect format for supplied string");
        }
        return toReturn;
    }

    @Override
    public String generateReceipt(List<Product> cartItems) {
        String toReturn = "";
        Double totalSalesTax = 0.0;
        Double totalPriceTaxFree = 0.0;
        var iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            var currentProduct = iterator.next();
            Boolean isProductTaxFree = TaxUtility.isProductTaxFree(currentProduct);
            Double standardTax =  isProductTaxFree ?  0.0 : TaxUtility.calculateStandardTax(currentProduct, basicTax);
            Double importTax = currentProduct.getIsProductImported() ? TaxUtility.calculateImportTax(currentProduct, importTaxVal) : 0.0;
            var costOfProduct = currentProduct.getPrice() + standardTax + importTax;
            totalSalesTax += importTax + standardTax;
            totalPriceTaxFree += currentProduct.getPrice();
            toReturn += String.format(Locale.US, "%d %s: %.2f\n", currentProduct.getQuantity(), currentProduct.getName(), costOfProduct);
        }
        toReturn += String.format(Locale.US, "Sales Taxes: %.2f\nTotal: %.2f", totalSalesTax, (totalPriceTaxFree+totalSalesTax));
        return toReturn;
    }
}
