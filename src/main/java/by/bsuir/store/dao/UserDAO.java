package by.bsuir.store.dao;

import by.bsuir.store.domain.User;

public interface UserDAO extends GenericDAO<User, Integer> {
    void addToBlacklist(Integer id) throws DAOException;

    void removeFromBlacklist(Integer id) throws DAOException;

    boolean checkUser(String login) throws DAOException;

    User findUserByLoginAndPassword(String login, String password) throws DAOException;
}
