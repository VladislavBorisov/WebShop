package by.bsuir.store.controller.command.impl;

import by.bsuir.store.controller.command.ICommand;
import by.bsuir.store.domain.Product;
import by.bsuir.store.domain.User;
import by.bsuir.store.resource.ConfigurationManager;
import by.bsuir.store.resource.MessageManager;
import by.bsuir.store.service.ProductService;
import by.bsuir.store.service.impl.ProductServiceImpl;
import by.bsuir.store.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProductToBasketCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(AddProductToBasketCommand.class);
    private static final String PARAM_NAME_PRODUCT_ID = "id";
    private static final String BASKET = "basket";
    private static final String MAP_ID_BASKET_AMOUNT = "amount_map";
    private static final String MESSAGE = "message";
    private static final String USER = "user";
    private static final String PRODUCT = "product";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String PRODUCT_PAGE = "path.page.product";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int id = Integer.parseInt(request.getParameter(PARAM_NAME_PRODUCT_ID));
        User user = (User) request.getSession().getAttribute(USER);
        List<Product> basket = (List<Product>) request.getSession().getAttribute(BASKET);
        Map<Integer, Integer> map = (Map<Integer, Integer>) request.getSession().getAttribute(MAP_ID_BASKET_AMOUNT);

        if (map == null) {
            map = new HashMap<>();
        }
        if (basket == null) {
            basket = new ArrayList<>();
        }

        try {
            ProductService productService = ProductServiceImpl.getInstance();
            Product product = productService.findById(id);
            if (user != null && product != null) {
                if (product.getAmount() != 0) {
                    if (map.containsKey(product.getProductId())) {
                        map.replace(product.getProductId(), map.get(product.getProductId()) + 1);
                    } else {
                        map.put(product.getProductId(), 1);
                        basket.add(product);
                    }
                    product.setAmount(product.getAmount() - 1);
                    productService.update(product);
                    Product newProduct = productService.findById(id);
                    request.getSession().setAttribute(PRODUCT, product);
                    request.getSession().setAttribute(BASKET, basket);
                    request.getSession().setAttribute(MAP_ID_BASKET_AMOUNT, map);
                    page = ConfigurationManager.getProperty(PRODUCT_PAGE);
                    request.setAttribute(MESSAGE, MessageManager.ADD_PRODUCT_TO_BASKET_SUCCESS);
                    request.getSession().setAttribute(URL, page);
                } else {
                    page = ConfigurationManager.getProperty(PRODUCT_PAGE);
                    request.setAttribute(MESSAGE, MessageManager.PRODUCT_NULL_AMOUNT);
                    request.getSession().setAttribute(URL, page);
                }
            } else {
                page = ConfigurationManager.getProperty(PRODUCT_PAGE);
                request.setAttribute(MESSAGE, MessageManager.ADD_PRODUCT_TO_BASKET_ERROR);
                request.getSession().setAttribute(URL, page);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error during adding product to basket: " + e);
            request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }
}
