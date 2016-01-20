package com.web.shop.controller.command.impl.navigation;

import javax.servlet.http.HttpServletRequest;

import com.web.shop.controller.command.ICommand;
import com.web.shop.resource.ConfigurationManager;

public class ToSignupCommand implements ICommand {
    private static String URL = "url";
    private static final String SIGN_UP_PAGE = "path.page.signup";

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(SIGN_UP_PAGE);
        request.getSession().setAttribute(URL, page);
        return page;
    }
}
