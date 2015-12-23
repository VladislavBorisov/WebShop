package by.bsuir.store.controller.command.impl;

import by.bsuir.store.resource.ConfigurationManager;
import by.bsuir.store.controller.command.ICommand;
import by.bsuir.store.domain.Order;
import by.bsuir.store.domain.User;
import by.bsuir.store.resource.MessageManager;
import by.bsuir.store.service.OrderService;
import by.bsuir.store.service.ServiceException;
import by.bsuir.store.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeleteOrderCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(DeleteOrderCommand.class);
    private static final String ORDER_ID = "order_id";
    private static final String MESSAGE = "message";
    private static final String USER = "user";
    private static final String USER_ORDERS = "userOrders";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String ORDERS_PAGE = "path.page.orders";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
        User user = (User) request.getSession().getAttribute(USER);
        int userId = user.getUserId();

        if (orderId != 0) {
            OrderService orderService = OrderServiceImpl.getInstance();
            try {
                orderService.delete(orderId);
                List<Order> orders = orderService.findById(userId);
                request.getSession().setAttribute(USER_ORDERS, orders);
                page = ConfigurationManager.getProperty(ORDERS_PAGE);
                request.setAttribute(MESSAGE, MessageManager.DELETE_ORDER_SUCCESS);
                request.getSession().setAttribute(URL, page);
            } catch (ServiceException e) {
                LOGGER.error("Error during deleting order: " + e);
                request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
                page = ConfigurationManager.getProperty(ERROR_PAGE);
            }
        }
        return page;
    }
}
