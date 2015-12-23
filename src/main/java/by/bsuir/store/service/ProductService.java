package by.bsuir.store.service;

import by.bsuir.store.domain.Product;

import java.util.List;

public interface ProductService {
    Product findById(Integer id) throws ServiceException;

    List<Product> allProducts() throws ServiceException;

    void delete(Integer id) throws ServiceException;

    void create(Product product) throws ServiceException;

    void update(Product product) throws ServiceException;
}
