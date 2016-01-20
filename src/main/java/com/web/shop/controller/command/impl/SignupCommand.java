package com.web.shop.controller.command.impl;


import org.apache.log4j.Logger;

import com.web.shop.controller.command.ICommand;
import com.web.shop.domain.User;
import com.web.shop.resource.ConfigurationManager;
import com.web.shop.resource.MessageManager;
import com.web.shop.service.ServiceException;
import com.web.shop.service.UserService;
import com.web.shop.service.impl.UserServiceImpl;
import com.web.shop.util.Coder;
import com.web.shop.validator.UserValidator;
import com.web.shop.validator.Validator;

import javax.servlet.http.HttpServletRequest;

public class SignupCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(SignupCommand.class);
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_FIRST_NAME = "firstName";
    private static final String PARAM_NAME_LAST_NAME = "lastName";
    private static final String PARAM_NAME_ADDRESS = "address";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String SIGN_UP_PAGE = "path.page.signup";
    private static final String MAIN_PAGE = "path.page.main";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String MESSAGE = "message";
    private static final String USER = "user";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        Validator<User> validator = new UserValidator();
        String login = request.getParameter(PARAM_NAME_LOGIN).trim();
        String password = Coder.hashMD5(request.getParameter(PARAM_NAME_PASSWORD));
        String firstName = request.getParameter(PARAM_NAME_FIRST_NAME).trim();
        String lastName = request.getParameter(PARAM_NAME_LAST_NAME).trim();
        String address = request.getParameter(PARAM_NAME_ADDRESS).trim();
        String email = request.getParameter(PARAM_NAME_EMAIL).trim();
        User user = new User(login, password, firstName, lastName, email, address);

        String result = validator.isValid(user);

        if (result == null) {
            UserService userService = UserServiceImpl.getInstance();
            try {
                if (!userService.isUserExist(user)) {
                    user = userService.create(user);
                    if (user == null) {
                        request.setAttribute(MESSAGE, MessageManager.LOGIN_ERROR);
                        page = ConfigurationManager.getProperty(SIGN_UP_PAGE);
                    } else {
                        request.getSession().setAttribute(USER, user);
                        page = ConfigurationManager.getProperty(MAIN_PAGE);
                        request.getSession().setAttribute(URL, page);
                        request.setAttribute(MESSAGE, MessageManager.SIGN_UP_SUCCESS);
                    }
                } else {
                    request.setAttribute(MESSAGE, MessageManager.SIGN_UP_ERROR_EXIST);
                    page = ConfigurationManager.getProperty(SIGN_UP_PAGE);
                }
            } catch (ServiceException e) {
                LOGGER.error("Error. Can't sign up user. " + e);
                request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
                page = ConfigurationManager.getProperty(ERROR_PAGE);
            }
        } else {
            request.setAttribute(MESSAGE, result);
            page = ConfigurationManager.getProperty(SIGN_UP_PAGE);
        }
        return page;
    }
}
