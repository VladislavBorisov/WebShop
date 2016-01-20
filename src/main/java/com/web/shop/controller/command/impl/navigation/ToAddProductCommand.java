package com.web.shop.controller.command.impl.navigation;

import javax.servlet.http.HttpServletRequest;

import com.web.shop.controller.command.ICommand;
import com.web.shop.resource.ConfigurationManager;

public class ToAddProductCommand implements ICommand {
    private static String URL = "url";
    private static String ADD_PRODUCT_PAGE = "path.page.admin.new.product";

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(ADD_PRODUCT_PAGE);
        request.getSession().setAttribute(URL, page);
        return page;
    }
}
