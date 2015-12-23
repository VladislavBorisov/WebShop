package by.bsuir.store.controller.command.impl;

import by.bsuir.store.controller.command.ICommand;
import by.bsuir.store.domain.User;
import by.bsuir.store.resource.ConfigurationManager;
import by.bsuir.store.resource.MessageManager;
import by.bsuir.store.service.UserService;
import by.bsuir.store.service.impl.UserServiceImpl;
import by.bsuir.store.util.Coder;
import by.bsuir.store.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String LOGIN_PAGE = "path.page.login";
    private static final String MAIN_PAGE = "path.page.main";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String MESSAGE = "message";
    private static final String USER = "user";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        UserService userService;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = Coder.hashMD5(request.getParameter(PARAM_NAME_PASSWORD));
        if (login.isEmpty() || password.isEmpty()) {
            request.setAttribute(MESSAGE, MessageManager.LOGIN_ERROR);
            page = ConfigurationManager.getProperty(LOGIN_PAGE);
            return page;
        }
        try {
            userService = UserServiceImpl.getInstance();
            User user = userService.signIn(login, password);
            if (user == null) {
                request.setAttribute(MESSAGE, MessageManager.LOGIN_ERROR);
                page = ConfigurationManager.getProperty(LOGIN_PAGE);
            } else if (user.isBlackList() == true) {
                request.setAttribute(MESSAGE, MessageManager.BLACK_LIST);
                page = ConfigurationManager.getProperty(LOGIN_PAGE);
            } else {
                request.getSession().setAttribute(USER, user);
                page = ConfigurationManager.getProperty(MAIN_PAGE);
                request.getSession().setAttribute(URL, page);
                LOGGER.error("Scc");
            }
        } catch (ServiceException e) {
            LOGGER.error("Error during user login: " + e);
            request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }
}
