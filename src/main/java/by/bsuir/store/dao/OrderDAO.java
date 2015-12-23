package by.bsuir.store.dao;

import by.bsuir.store.domain.Order;

import java.util.List;

public interface OrderDAO extends GenericDAO<Order, Integer> {
    void addProductToOrder(Order order) throws DAOException;

    List<Order> findOrdersByUserId(Integer id) throws DAOException;

    void deleteProductFromOrder(Integer id) throws DAOException;

    void productsFromOrder(Order order) throws DAOException;
}
