package com.web.shop.service;

import java.util.List;

import com.web.shop.domain.Order;

public interface OrderService {

    void create(Order order) throws ServiceException;

    List<Order> findById(Integer id) throws ServiceException;

    void delete(Integer orderId) throws ServiceException;
}
