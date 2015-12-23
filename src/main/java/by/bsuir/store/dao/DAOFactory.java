package by.bsuir.store.dao;

import by.bsuir.store.dao.impl.OrderDAOImpl;
import by.bsuir.store.dao.impl.ProductDAOImpl;
import by.bsuir.store.dao.impl.UserDAOImpl;

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
