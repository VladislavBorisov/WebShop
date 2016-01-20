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

public class AllUserOrdersCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(AllUserOrdersCommand.class);
    private static final String USER = "user";
    private static final String MESSAGE = "message";
    private static final String USER_ORDERS = "userOrders";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String ORDERS_PAGE = "path.page.orders";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        User user = (User) request.getSession().getAttribute(USER);
        int id = user.getUserId();
        List<Order> orderList;

        if (id != 0) {
            OrderService orderService = OrderServiceImpl.getInstance();
            try {
                orderList = orderService.findById(id);
                if (!orderList.isEmpty()) {
                    request.getSession().setAttribute(USER_ORDERS, orderList);
                    page = ConfigurationManager.getProperty(ORDERS_PAGE);
                    request.getSession().setAttribute(URL, page);
                } else {
                    page = ConfigurationManager.getProperty(ORDERS_PAGE);
                    request.getSession().setAttribute(URL, page);
                }
            } catch (ServiceException e) {
                LOGGER.error("Error during getting all user orders: " + e);
                request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
                page = ConfigurationManager.getProperty(ERROR_PAGE);
            }
        }
        return page;
    }
}
