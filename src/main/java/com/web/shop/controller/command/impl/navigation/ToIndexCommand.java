package com.web.shop.controller.command.impl.navigation;

import javax.servlet.http.HttpServletRequest;

import com.web.shop.controller.command.ICommand;
import com.web.shop.resource.ConfigurationManager;

public class ToIndexCommand implements ICommand {
    private static String URL = "url";
    private static String INDEX_PAGE = "path.page.login";

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(INDEX_PAGE);
        request.getSession().setAttribute(URL, page);
        return page;
    }
}
