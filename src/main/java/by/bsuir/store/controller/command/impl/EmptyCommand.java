package by.bsuir.store.controller.command.impl;

import by.bsuir.store.resource.ConfigurationManager;
import by.bsuir.store.controller.command.ICommand;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements ICommand {
	private static final String LOGIN_PAGE = "path.page.login";

	@Override
	public String execute(HttpServletRequest request) {

		String page = ConfigurationManager.getProperty(LOGIN_PAGE);
		return page;
	}
}