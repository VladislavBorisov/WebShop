package com.web.shop.service;

import java.util.List;

import com.web.shop.domain.Product;

public interface ProductService {
    Product findById(Integer id) throws ServiceException;

    List<Product> allProducts() throws ServiceException;

    void delete(Integer id) throws ServiceException;

    void create(Product product) throws ServiceException;

    void update(Product product) throws ServiceException;
}
