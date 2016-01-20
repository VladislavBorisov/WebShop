package com.web.shop.validator;

import java.util.regex.Pattern;

import com.web.shop.domain.Product;
import com.web.shop.resource.MessageManager;

public class ProductValidator implements Validator<Product> {
    public static final Pattern PRICE_PATTERN = Pattern.compile("[0-9]{1,13}(\\.[0-9]*)?");
    public static final Pattern CATEGORY_PATTERN = Pattern.compile("^[a-z]+$");

    @Override
    public String isValid(Product product) {
        String price = String.valueOf(product.getPrice());
        if (!PRICE_PATTERN.matcher(price).matches()) {
            return MessageManager.INCORRECT_PRODUCT_PRICE;
        }
        if (!CATEGORY_PATTERN.matcher(product.getCategory()).find()) {
            return MessageManager.INCORRECT_PRODUCT_CATEGORY;
        }
        return null;
    }
}
