package com.web.shop.controller.command.impl;

import org.apache.log4j.Logger;

import com.web.shop.controller.command.ICommand;
import com.web.shop.domain.Order;
import com.web.shop.domain.User;
import com.web.shop.resource.ConfigurationManager;
import com.web.shop.resource.MessageManager;
import com.web.shop.service.OrderService;
import com.web.shop.service.ServiceException;
import com.web.shop.service.impl.OrderServiceImpl;

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
