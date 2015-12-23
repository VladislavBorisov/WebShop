package by.bsuir.store.controller.command.impl.navigation;

import by.bsuir.store.resource.ConfigurationManager;
import by.bsuir.store.controller.command.ICommand;

import javax.servlet.http.HttpServletRequest;

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
