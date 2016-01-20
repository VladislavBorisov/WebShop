package com.web.shop.controller.command.impl.admin;

import org.apache.log4j.Logger;

import com.web.shop.controller.command.ICommand;
import com.web.shop.domain.User;
import com.web.shop.resource.ConfigurationManager;
import com.web.shop.resource.MessageManager;
import com.web.shop.service.ServiceException;
import com.web.shop.service.UserService;
import com.web.shop.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RemoveFromBlackListCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(RemoveFromBlackListCommand.class);
    private static final String PARAM_NAME_USER_ID = "id";
    private static final String USERS_ADMIN_PAGE = "path.page.admin.users";
    private static final String USERS = "users";
    private static final String URL = "url";
    private static final String MESSAGE = "message";
    private static final String ERROR_PAGE = "path.page.error";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        int id = Integer.parseInt(request.getParameter(PARAM_NAME_USER_ID));
        UserService userService = UserServiceImpl.getInstance();
        try {
            userService.removeFromBlackList(id);
            List<User> users = userService.allUsers();
            if (users != null) {
                request.getSession().setAttribute(USERS, users);
                page = ConfigurationManager.getProperty(USERS_ADMIN_PAGE);
                request.getSession().setAttribute(URL, page);
            } else {
                page = ConfigurationManager.getProperty(USERS_ADMIN_PAGE);
                request.setAttribute(MESSAGE, MessageManager.EMPTY_USER_LIST);
                request.getSession().setAttribute(URL, page);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error during user removing user from blacklist: " + e);
            request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }
}
