package com.web.shop.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, K extends Serializable> {
    public List<T> findAll() throws DAOException;

    void create(T t) throws DAOException;

    void delete(K id) throws DAOException;

    T find(K id) throws DAOException;

    void update(T t) throws DAOException;
}
