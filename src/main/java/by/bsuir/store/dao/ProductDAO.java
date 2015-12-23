package by.bsuir.store.dao;

import by.bsuir.store.domain.Product;

public interface ProductDAO extends GenericDAO<Product, Integer> {
    int findCategoryByName(String category) throws DAOException;
}
