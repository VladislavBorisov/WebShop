package com.web.shop.dao.impl;

import com.mysql.jdbc.Statement;
import com.web.shop.dao.DAOException;
import com.web.shop.dao.ProductDAO;
import com.web.shop.dao.pool.ConnectionPoolException;
import com.web.shop.dao.pool.DBConnectionPool;
import com.web.shop.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    private static final String SQL_All_PRODUCTS = "SELECT * FROM store.products JOIN store.categories ON products.category=categories.category_id";
    private static final String SQL_FIND_PRODUCT_BY_ID = "SELECT * FROM store.products JOIN store.categories ON products.category=categories.category_id WHERE product_id=?";
    private static final String SQL_DELETE_PRODUCT = "DELETE FROM store.products WHERE product_id=?";
    private static final String SQL_ADD_NEW_PRODUCT = "INSERT INTO store.products(product_name, price, description, category, image, amount) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_CATEGORY = "SELECT * FROM store.categories WHERE categories.name=?";
    private static final String SQL_UPDATE_PRODUCT = "UPDATE store.products SET product_name=?, price=?, description=?, category=?, image=?, amount=? WHERE product_id = ?";
    private static final String SQL_PRODUCT_ID = "product_id";
    private static final String SQL_NAME = "product_name";
    private static final String SQL_PRICE = "price";
    private static final String SQL_DESCRIPTION = "description";
    private static final String SQL_CATEGORIES_NAME = "categories.name";
    private static final String SQL_CATEGORY_ID = "categories.category_id";
    private static final String SQL_IMAGE = "image";
    private static final String SQL_AMOUNT = "amount";

    public List<Product> findAll() throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        Product product;
        List<Product> productList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_All_PRODUCTS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product = new Product();
                product.setProductId(resultSet.getInt(SQL_PRODUCT_ID));
                product.setName(resultSet.getString(SQL_NAME));
                product.setPrice(resultSet.getDouble(SQL_PRICE));
                product.setDescription(resultSet.getString(SQL_DESCRIPTION));
                product.setCategory(resultSet.getString(SQL_CATEGORIES_NAME));
                product.setImage(resultSet.getString(SQL_IMAGE));
                product.setAmount(resultSet.getInt(SQL_AMOUNT));
                productList.add(product);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                dbConnectionPool.closeConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                throw new DAOException(e);
            }
        }
        return productList;
    }

    @Override
    public void create(Product product) throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            int categoryId = findCategoryByName(product.getCategory().toLowerCase());
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_NEW_PRODUCT,  Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, categoryId);
            preparedStatement.setString(5, product.getImage());
            preparedStatement.setInt(6, product.getAmount());
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Updating product failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setProductId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
        } catch (SQLException | DAOException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                dbConnectionPool.closeConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public void delete(Integer id) throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_PRODUCT);
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Deleting product failed, no rows affected.");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                dbConnectionPool.closeConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public Product find(Integer id) throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        Product product = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_PRODUCT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product();
                product.setProductId(resultSet.getInt(SQL_PRODUCT_ID));
                product.setName(resultSet.getString(SQL_NAME));
                product.setPrice(resultSet.getDouble(SQL_PRICE));
                product.setDescription(resultSet.getString(SQL_DESCRIPTION));
                product.setCategory(resultSet.getString(SQL_CATEGORIES_NAME));
                product.setImage(resultSet.getString(SQL_IMAGE));
                product.setAmount(resultSet.getInt(SQL_AMOUNT));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                dbConnectionPool.closeConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                throw new DAOException(e);
            }
        }
        return product;
    }

    @Override
    public void update(Product product) throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            int categoryId = findCategoryByName(product.getCategory().toLowerCase());
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_PRODUCT);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, categoryId);
            preparedStatement.setString(5, product.getImage());
            preparedStatement.setInt(6, product.getAmount());
            preparedStatement.setInt(7, product.getProductId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Updating product failed, no rows affected.");
            }
        } catch (SQLException | DAOException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                dbConnectionPool.closeConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                throw new DAOException(e);
            }
        }
    }

    public int findCategoryByName(String category) throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        int result = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_CATEGORY);
            preparedStatement.setString(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(SQL_CATEGORY_ID);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                dbConnectionPool.closeConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }
}
