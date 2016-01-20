package com.web.shop.dao.impl;

import com.mysql.jdbc.Statement;
import com.web.shop.dao.DAOException;
import com.web.shop.dao.UserDAO;
import com.web.shop.dao.pool.ConnectionPoolException;
import com.web.shop.dao.pool.DBConnectionPool;
import com.web.shop.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final static String SQL_FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM store.users WHERE login=? AND password=?";
    private final static String SQL_USER = "SELECT * FROM store.users WHERE login=?";
    private final static String SQL_USER_BY_ID = "SELECT * FROM store.users WHERE user_id=?";
    private final static String SQL_SIGN_UP_NEW_USER = "INSERT INTO store.users(login, password, first_name, last_name, address, email) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String SQL_ALL_USERS = "SELECT * FROM store.users WHERE role=0";
    private final static String SQl_ADD_TO_BLACKLIST = "UPDATE store.users SET black_list=1 WHERE user_id=?";
    private final static String SQl_REMOVE_FROM_BLACKLIST = "UPDATE store.users SET black_list=0 WHERE user_id=?";
    private final static String SQL_DELETE_USER = "DELETE FROM store.users WHERE user_id=?";
    private final static String SQL_USER_ID = "user_id";
    private final static String SQL_USER_FIRST_NAME = "first_name";
    private final static String SQL_USER_LAST_NAME = "last_name";
    private final static String SQL_USER_LOGIN = "login";
    private final static String SQL_USER_PASSWORD = "password";
    private final static String SQL_USER_ROLE = "role";
    private final static String SQL_USER_BLACK_LIST = "black_list";
    private final static String SQL_USER_ADDRESS = "address";
    private final static String SQL_USER_EMAIL = "email";

    public User findUserByLoginAndPassword(String login, String password) throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt(SQL_USER_ID));
                user.setLogin(resultSet.getString(SQL_USER_LOGIN));
                user.setPassword(resultSet.getString(SQL_USER_PASSWORD));
                user.setFirstName(resultSet.getString(SQL_USER_FIRST_NAME));
                user.setLastName(resultSet.getString(SQL_USER_LAST_NAME));
                user.setRole(resultSet.getInt(SQL_USER_ROLE));
                user.setBlackList(resultSet.getBoolean(SQL_USER_BLACK_LIST));
                user.setAddress(resultSet.getString(SQL_USER_ADDRESS));
                user.setEmail(resultSet.getString(SQL_USER_EMAIL));
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
        return user;
    }

    public boolean checkUser(String login) throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        boolean result = false;
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_USER);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.first();
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

    public List<User> findAll() throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        User user = null;
        List<User> userList = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            userList = new ArrayList<>();
            while (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setFirstName(resultSet.getString(4));
                user.setLastName(resultSet.getString(5));
                user.setBlackList(resultSet.getBoolean(7));
                user.setAddress(resultSet.getString(8));
                user.setEmail(resultSet.getString(9));
                userList.add(user);
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
        return userList;
    }

    @Override
    public void create(User user) throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SIGN_UP_NEW_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getEmail());
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
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
    public void delete(Integer id) throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_USER);
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
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
    public User find(Integer id) throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_USER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt(SQL_USER_ID));
                user.setLogin(resultSet.getString(SQL_USER_LOGIN));
                user.setPassword(resultSet.getString(SQL_USER_PASSWORD));
                user.setFirstName(resultSet.getString(SQL_USER_FIRST_NAME));
                user.setLastName(resultSet.getString(SQL_USER_LAST_NAME));
                user.setRole(resultSet.getInt(SQL_USER_ROLE));
                user.setBlackList(resultSet.getBoolean(SQL_USER_BLACK_LIST));
                user.setAddress(resultSet.getString(SQL_USER_ADDRESS));
                user.setEmail(resultSet.getString(SQL_USER_EMAIL));
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
        return user;
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addToBlacklist(Integer id) throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQl_ADD_TO_BLACKLIST);
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Adding user to blacklist failed, no rows affected.");
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
    public void removeFromBlacklist(Integer id) throws DAOException {
        DBConnectionPool dbConnectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            connection = dbConnectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQl_REMOVE_FROM_BLACKLIST);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Removing user from blacklist failed, no rows affected.");
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

}