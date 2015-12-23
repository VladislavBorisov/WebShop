package by.bsuir.store.controller.command.impl.navigation;

import by.bsuir.store.controller.command.ICommand;
import by.bsuir.store.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

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
