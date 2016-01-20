package com.web.shop.service.impl;

import java.util.List;

import com.web.shop.dao.DAOException;
import com.web.shop.dao.DAOFactory;
import com.web.shop.dao.ProductDAO;
import com.web.shop.domain.Product;
import com.web.shop.service.ProductService;
import com.web.shop.service.ServiceException;

public class ProductServiceImpl implements ProductService {
    private static ProductService instance = new ProductServiceImpl();

    private ProductServiceImpl() {
    }

    public static ProductService getInstance() {
        return instance;
    }

    @Override
    public Product findById(Integer id) throws ServiceException {
        Product product;
        ProductDAO productDAO = DAOFactory.getProductDAO();
        try {
            product = productDAO.find(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in product service", e);
        }
        return product;
    }

    @Override
    public List<Product> allProducts() throws ServiceException {
        List<Product> productList;
        ProductDAO productDAO = DAOFactory.getProductDAO();
        try {
            productList = productDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException("Exception in product service", e);
        }
        return productList;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        ProductDAO productDAO = DAOFactory.getProductDAO();
        try {
           productDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in product service", e);
        }
    }

    @Override
    public void create(Product product) throws ServiceException {
        ProductDAO productDAO = DAOFactory.getProductDAO();
        try {
            productDAO.create(product);
        } catch (DAOException e) {
            throw new ServiceException("Exception in product service", e);
        }
    }

    @Override
    public void update(Product product) throws ServiceException {
        ProductDAO productDAO = DAOFactory.getProductDAO();
        try {
            productDAO.update(product);
        } catch (DAOException e) {
            throw new ServiceException("Exception in product service", e);
        }
    }
}
