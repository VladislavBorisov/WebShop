package by.bsuir.store.controller.command.impl.admin;

import by.bsuir.store.controller.command.ICommand;
import by.bsuir.store.domain.User;
import by.bsuir.store.resource.ConfigurationManager;
import by.bsuir.store.resource.MessageManager;
import by.bsuir.store.service.ServiceException;
import by.bsuir.store.service.UserService;
import by.bsuir.store.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AllUsersCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(AllUsersCommand.class);
    private static final String USERS = "users";
    private static final String URL = "url";
    private static final String MESSAGE = "message";
    private static final String USERS_ADMIN_PAGE = "path.page.admin.users";
    private static final String ERROR_PAGE = "path.page.error";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        UserService service = UserServiceImpl.getInstance();
        try {
            List<User> users = service.allUsers();
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
            LOGGER.error("Error during obtaining users: " + e);
            request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }
}
