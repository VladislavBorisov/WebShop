package com.web.shop.validator;

public interface Validator<T> {
    String isValid(T t);
}
