package by.bsuir.store.controller.command.impl.navigation;

import by.bsuir.store.controller.command.ICommand;
import by.bsuir.store.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class ToBasketCommand implements ICommand {
    private static String URL = "url";
    private static String BASKET_PAGE = "path.page.basket";

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(BASKET_PAGE);
        request.getSession().setAttribute(URL, page);
        return page;
    }
}
