package by.bsuir.store.controller.command.impl.navigation;

import by.bsuir.store.controller.command.ICommand;
import by.bsuir.store.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

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
