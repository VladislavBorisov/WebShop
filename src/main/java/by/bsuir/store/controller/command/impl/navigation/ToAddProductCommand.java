package by.bsuir.store.controller.command.impl.navigation;

import by.bsuir.store.controller.command.ICommand;
import by.bsuir.store.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class ToAddProductCommand implements ICommand {
    private static String URL = "url";
    private static String ADD_PRODUCT_PAGE = "path.page.admin.new.product";

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(ADD_PRODUCT_PAGE);
        request.getSession().setAttribute(URL, page);
        return page;
    }
}
