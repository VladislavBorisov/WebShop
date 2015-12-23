package by.bsuir.store.controller.command.impl;

import by.bsuir.store.domain.Product;
import by.bsuir.store.resource.ConfigurationManager;
import by.bsuir.store.resource.MessageManager;
import by.bsuir.store.service.ProductService;
import by.bsuir.store.service.impl.ProductServiceImpl;
import by.bsuir.store.controller.command.ICommand;
import by.bsuir.store.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ProductViewCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(ProductViewCommand.class);
    private static final String PRODUCT_ID = "id";
    private static final String MESSAGE = "message";
    private static final String PRODUCT = "product";
    private static final String PRODUCT_PAGE = "path.page.product";
    private static final String CATALOG_PAGE = "path.page.catalog";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int id = Integer.parseInt(request.getParameter(PRODUCT_ID));
        ProductService productService = ProductServiceImpl.getInstance();
        Product product = null;
        try {
            product = productService.findById(id);
            if (product != null) {
                request.getSession().setAttribute(PRODUCT, product);
                page = ConfigurationManager.getProperty(PRODUCT_PAGE);
                request.getSession().setAttribute(URL, page);
            } else {
                request.setAttribute(MESSAGE, MessageManager.PRODUCT_NOT_FOUND);
                page = ConfigurationManager.getProperty(CATALOG_PAGE);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error. Can't show product. " + e);
            request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }
}
