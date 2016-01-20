package com.web.shop.controller.command.impl.admin;

import org.apache.log4j.Logger;

import com.web.shop.controller.command.ICommand;
import com.web.shop.domain.Product;
import com.web.shop.resource.ConfigurationManager;
import com.web.shop.resource.MessageManager;
import com.web.shop.service.ProductService;
import com.web.shop.service.ServiceException;
import com.web.shop.service.impl.ProductServiceImpl;
import com.web.shop.validator.ProductValidator;
import com.web.shop.validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class AddProductCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(AddProductCommand.class);
    private static final String PICTURE_UPLOAD_PATH = "images" + File.separator;
    private static final String IMAGE_MIME_TYPE = "image/";
    private static final String FILE = "file";
    private static final String PRODUCTS = "products";
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_PRICE = "price";
    private static final String PARAM_NAME_DESCRIPTION = "description";
    private static final String PARAM_NAME_CATEGORY_NAME = "category";
    private static final String PARAM_NAME_AMOUNT = "amount";
    private static final String MESSAGE = "message";
    private static final String CATALOG_PAGE = "path.page.catalog";
    private static final String NEW_PRODUCT_PAGE = "path.page.admin.new.product";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String filename = null;
        String name = request.getParameter(PARAM_NAME_NAME).trim();
        String price = request.getParameter(PARAM_NAME_PRICE).trim();
        String description = request.getParameter(PARAM_NAME_DESCRIPTION).trim();
        String category = request.getParameter(PARAM_NAME_CATEGORY_NAME).trim();
        String amount = request.getParameter(PARAM_NAME_AMOUNT).trim();

        try {
            Part filePart = request.getPart(FILE);
            filename = filePart.getSubmittedFileName();
            if (!filename.isEmpty()) {
                String mimeType = request.getServletContext().getMimeType(filename);
                if (mimeType.startsWith(IMAGE_MIME_TYPE)) {
                    File uploads = new File(request.getServletContext().getRealPath("") + PICTURE_UPLOAD_PATH);
                    File file = new File(uploads, filename);
                    try (InputStream input = filePart.getInputStream()) {
                        Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                } else {
                    request.setAttribute(MESSAGE, MessageManager.NOT_JPG_IMAGE);
                    page = ConfigurationManager.getProperty(NEW_PRODUCT_PAGE);
                    return page;
                }
            }
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (ServletException e) {
            LOGGER.error(e);
        }

        String image = filename;
        if (name.isEmpty() || price.isEmpty() || description.isEmpty() || category.isEmpty() || image.isEmpty() || amount.isEmpty()) {
            request.setAttribute(MESSAGE, MessageManager.NULL_PRODUCT_INPUT);
            page = ConfigurationManager.getProperty(NEW_PRODUCT_PAGE);
            return page;
        }

        Validator validator = new ProductValidator();
        Product product = new Product();
        product.setName(name);
        product.setPrice(Double.parseDouble(price));
        product.setDescription(description);
        product.setCategory(category);
        product.setImage(image);
        product.setAmount(Integer.parseInt(amount));

        String validationProductResult = validator.isValid(product);

        if (validationProductResult == null) {
            ProductService productService = ProductServiceImpl.getInstance();
            try {
                productService.create(product);
                List<Product> products = productService.allProducts();
                request.getSession().setAttribute(PRODUCTS, products);
                page = ConfigurationManager.getProperty(CATALOG_PAGE);
                request.getSession().setAttribute(URL, page);
            } catch (ServiceException e) {
                LOGGER.error("Error during adding product: " + e);
                request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
                page = ConfigurationManager.getProperty(ERROR_PAGE);
            }
        } else {
            request.setAttribute(MESSAGE, validationProductResult);
            page = ConfigurationManager.getProperty(NEW_PRODUCT_PAGE);
        }
        return page;
    }

}