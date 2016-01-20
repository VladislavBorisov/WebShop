package com.web.shop.controller.command.impl;


import org.apache.log4j.Logger;

import com.web.shop.controller.command.ICommand;
import com.web.shop.domain.Product;
import com.web.shop.resource.ConfigurationManager;
import com.web.shop.resource.MessageManager;
import com.web.shop.service.ProductService;
import com.web.shop.service.ServiceException;
import com.web.shop.service.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LogoutCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);
    private static final String INDEX_PAGE = "path.page.index";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String MESSAGE = "message";
    private static final String MAP_ID_BASKET_AMOUNT = "amount_map";

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(INDEX_PAGE);
        Map<Integer, Integer> map = (Map<Integer, Integer>) request.getSession().getAttribute(MAP_ID_BASKET_AMOUNT);
        if (map == null || map.isEmpty()) {
            request.getSession().invalidate();
        } else {
            ProductService productService = ProductServiceImpl.getInstance();
            try {
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    Product product = productService.findById(entry.getKey());
                    int currentAmount = product.getAmount();
                    product.setAmount(currentAmount + entry.getValue());
                    productService.update(product);
                }
                request.getSession().invalidate();
            } catch (ServiceException e) {
                LOGGER.error("Log out error: " + e);
                request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
                page = ConfigurationManager.getProperty(ERROR_PAGE);
            }
        }
        return page;
    }
}
