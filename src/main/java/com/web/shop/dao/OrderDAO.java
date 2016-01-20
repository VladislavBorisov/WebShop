package com.web.shop.dao;

import java.util.List;

import com.web.shop.domain.Order;

public interface OrderDAO extends GenericDAO<Order, Integer> {
    void addProductToOrder(Order order) throws DAOException;

    List<Order> findOrdersByUserId(Integer id) throws DAOException;

    void deleteProductFromOrder(Integer id) throws DAOException;

    void productsFromOrder(Order order) throws DAOException;
}
