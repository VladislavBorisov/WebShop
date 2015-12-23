package by.bsuir.store.service;

import by.bsuir.store.domain.Order;

import java.util.List;

public interface OrderService {//TODO Sonar Qube static code analyz

    void create(Order order) throws ServiceException;

    List<Order> findById(Integer id) throws ServiceException;

    void delete(Integer orderId) throws ServiceException;
}
