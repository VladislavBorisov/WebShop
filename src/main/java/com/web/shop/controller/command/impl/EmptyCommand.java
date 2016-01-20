package com.web.shop.controller.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.web.shop.controller.command.ICommand;
import com.web.shop.resource.ConfigurationManager;

public class EmptyCommand implements ICommand {
	private static final String LOGIN_PAGE = "path.page.login";

	@Override
	public String execute(HttpServletRequest request) {

		String page = ConfigurationManager.getProperty(LOGIN_PAGE);
		return page;
	}
}