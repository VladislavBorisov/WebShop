package com.web.shop.controller.command.impl.admin;

import org.apache.log4j.Logger;

import com.web.shop.controller.command.ICommand;
import com.web.shop.domain.Product;
import com.web.shop.resource.ConfigurationManager;
import com.web.shop.resource.MessageManager;
import com.web.shop.service.ProductService;
import com.web.shop.service.ServiceException;
import com.web.shop.service.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeleteProductCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(DeleteProductCommand.class);
    private static final String PARAM_NAME_PRODUCT_ID = "id";
    private static final String PRODUCTS = "products";
    private static final String MESSAGE = "message";
    private static final String CATALOG_PAGE = "path.page.catalog";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int id = Integer.parseInt(request.getParameter(PARAM_NAME_PRODUCT_ID));
        ProductService productService = ProductServiceImpl.getInstance();
        try {
            productService.delete(id);
            List<Product> products = productService.allProducts();
            if (!products.isEmpty()) {
                request.getSession().setAttribute(PRODUCTS, products);
                page = ConfigurationManager.getProperty(CATALOG_PAGE);
                request.getSession().setAttribute(URL, page);
            } else {
                page = ConfigurationManager.getProperty(CATALOG_PAGE);
                request.setAttribute(MESSAGE, MessageManager.HAVE_NOT_PRODUCTS);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error during deleting product: " + e);
            request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }
}
