package by.bsuir.store.service.impl;

import by.bsuir.store.dao.DAOException;
import by.bsuir.store.dao.DAOFactory;
import by.bsuir.store.dao.OrderDAO;
import by.bsuir.store.domain.Order;
import by.bsuir.store.domain.Product;
import by.bsuir.store.service.OrderService;
import by.bsuir.store.service.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private static OrderService instance = new OrderServiceImpl();

    private OrderServiceImpl() {
    }

    public static OrderService getInstance() {
        return instance;
    }

    @Override
    public void create(Order order) throws ServiceException {
        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        try {
            orderDAO.create(order);
            orderDAO.addProductToOrder(order);
        } catch (DAOException e) {
            throw new ServiceException("Exception in order service", e);
        }
    }

    @Override
    public List<Order> findById(Integer id) throws ServiceException {
        List<Order> orderList;
        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        try {
            orderList = orderDAO.findOrdersByUserId(id);
            for (Order order: orderList) {
                List<Product> listOfProducts = new ArrayList<>();
                Map<Integer, Integer> amountMap = new HashMap<>();
                order.setOrderList(listOfProducts);
                order.setAmountMap(amountMap);
                orderDAO.productsFromOrder(order);
            }
        } catch (DAOException e) {
            throw new ServiceException("Exception in order service", e);
        }
        return orderList;
    }

    @Override
    public void delete(Integer orderId) throws ServiceException {
        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        try {
            orderDAO.delete(orderId);
        } catch (DAOException e) {
            throw new ServiceException("Exception in order service", e);
        }
    }

}
