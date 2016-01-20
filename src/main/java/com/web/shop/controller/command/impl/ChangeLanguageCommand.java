package com.web.shop.controller.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.web.shop.controller.command.ICommand;

public class ChangeLanguageCommand implements ICommand {
    private String page;
    private static final String LOCALE = "locale";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String locale = request.getParameter(LOCALE);
        request.getSession().setAttribute(LOCALE, locale);
        page = (String) request.getSession().getAttribute(URL);
        return page;
    }
}
