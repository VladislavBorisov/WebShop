package com.web.shop.dao;

import com.web.shop.dao.impl.OrderDAOImpl;
import com.web.shop.dao.impl.ProductDAOImpl;
import com.web.shop.dao.impl.UserDAOImpl;

public class DAOFactory {
    private static UserDAO userDAO = new UserDAOImpl();
    private static ProductDAO productDAO = new ProductDAOImpl();
    private static OrderDAO orderDAO = new OrderDAOImpl();

    public static UserDAO getUserDAO() {
        return userDAO;
    }

    public static ProductDAO getProductDAO() {
        return productDAO;
    }

    public static OrderDAO getOrderDAO() {return orderDAO;}
}
