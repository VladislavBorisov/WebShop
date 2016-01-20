package com.web.shop.service;

import java.util.List;

import com.web.shop.domain.User;

public interface UserService {
    User signIn(String login, String password) throws ServiceException;

    List<User> allUsers() throws ServiceException;

    User create(User user) throws ServiceException;

    void addToBlackList(Integer id) throws ServiceException;

    void removeFromBlackList(Integer id) throws ServiceException;

    void delete(Integer id) throws ServiceException;

    boolean isUserExist(User user) throws ServiceException;
}
