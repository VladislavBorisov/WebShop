package by.bsuir.store.controller.command.impl.navigation;

import by.bsuir.store.service.impl.ProductServiceImpl;
import by.bsuir.store.controller.command.ICommand;
import by.bsuir.store.domain.Product;
import by.bsuir.store.resource.ConfigurationManager;
import by.bsuir.store.resource.MessageManager;
import by.bsuir.store.service.ProductService;
import by.bsuir.store.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ToEditCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(ToEditCommand.class);
    private static final String PRODUCT_ID = "id";
    private static final String URL = "url";
    private static final String PRODUCT = "product";
    private static final String MESSAGE = "message";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String ADMIN_EDIT_PAGE = "path.page.admin.edit";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int id = Integer.parseInt(request.getParameter(PRODUCT_ID));
        ProductService productService = ProductServiceImpl.getInstance();
        try {
            Product product = productService.findById(id);
            if (product != null) {
                request.getSession().setAttribute(PRODUCT, product);
                page = ConfigurationManager.getProperty(ADMIN_EDIT_PAGE);
                request.getSession().setAttribute(URL, page);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error during user removing user from blacklist: " + e);
            request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }
}
