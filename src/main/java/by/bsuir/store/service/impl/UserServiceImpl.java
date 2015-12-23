package by.bsuir.store.service.impl;

import by.bsuir.store.dao.DAOException;
import by.bsuir.store.domain.User;
import by.bsuir.store.dao.DAOFactory;
import by.bsuir.store.dao.UserDAO;
import by.bsuir.store.service.ServiceException;
import by.bsuir.store.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserService instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        return instance;
    }

    public User signIn(String login, String password) throws ServiceException {
        User user;
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            user = userDAO.findUserByLoginAndPassword(login, password);
        } catch (DAOException e) {
            throw new ServiceException("Exception in user service", e);
        }
        return user;
    }

    @Override
    public List<User> allUsers() throws ServiceException {
        List<User> userList;
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            userList = userDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException("Exception in user service", e);
        }
        return userList;
    }

    public User create(User user) throws ServiceException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            userDAO.create(user);
        } catch (DAOException e) {
            throw new ServiceException("Exception in user service", e);
        }
        return user;
    }

    @Override
    public void addToBlackList(Integer id) throws ServiceException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            userDAO.addToBlacklist(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in user service", e);
        }
    }

    @Override
    public void removeFromBlackList(Integer id) throws ServiceException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            userDAO.removeFromBlacklist(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in user service", e);
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            userDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in user service", e);
        }
    }

    public boolean isUserExist(User user) throws ServiceException {
        boolean result;
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            result = userDAO.checkUser(user.getLogin());
        } catch (DAOException e) {
            throw new ServiceException("Exception in user service", e);
        }
        return result;
    }
}
