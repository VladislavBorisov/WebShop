package com.web.shop.dao;

import com.web.shop.domain.Product;

public interface ProductDAO extends GenericDAO<Product, Integer> {
    int findCategoryByName(String category) throws DAOException;
}
