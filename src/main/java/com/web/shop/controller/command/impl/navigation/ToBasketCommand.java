package com.web.shop.controller.command.impl.navigation;

import javax.servlet.http.HttpServletRequest;

import com.web.shop.controller.command.ICommand;
import com.web.shop.resource.ConfigurationManager;

public class ToBasketCommand implements ICommand {
    private static String URL = "url";
    private static String BASKET_PAGE = "path.page.basket";

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(BASKET_PAGE);
        request.getSession().setAttribute(URL, page);
        return page;
    }
}
