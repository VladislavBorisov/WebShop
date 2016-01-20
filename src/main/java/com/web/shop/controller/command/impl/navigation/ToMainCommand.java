package com.web.shop.controller.command.impl.navigation;

import javax.servlet.http.HttpServletRequest;

import com.web.shop.controller.command.ICommand;
import com.web.shop.resource.ConfigurationManager;

public class ToMainCommand implements ICommand {
    private static String URL = "url";
    private static String MAIN_PAGE = "path.page.main";

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(MAIN_PAGE);
        request.getSession().setAttribute(URL, page);
        return page;
    }
}
