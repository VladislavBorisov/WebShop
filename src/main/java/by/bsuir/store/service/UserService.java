package by.bsuir.store.service;

import by.bsuir.store.domain.User;

import java.util.List;

public interface UserService {
    User signIn(String login, String password) throws ServiceException;

    List<User> allUsers() throws ServiceException;

    User create(User user) throws ServiceException;

    void addToBlackList(Integer id) throws ServiceException;

    void removeFromBlackList(Integer id) throws ServiceException;

    void delete(Integer id) throws ServiceException;

    boolean isUserExist(User user) throws ServiceException;
}
