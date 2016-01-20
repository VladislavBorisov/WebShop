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

public class EditProductCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(EditProductCommand.class);
    private static final String PICTURE_UPLOAD_PATH = "images" + File.separator;
    private static final String IMAGE_MIME_TYPE = "image/";
    private static final String FILE = "file";
    private static final String PARAM_NAME_PRODUCT_ID = "id";
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_PRICE = "price";
    private static final String PARAM_NAME_DESCRIPTION = "description";
    private static final String PARAM_NAME_IMAGE = "image";
    private static final String PARAM_NAME_CATEGORY_NAME = "category";
    private static final String PARAM_NAME_AMOUNT = "amount";
    private static final String MESSAGE = "message";
    private static final String PRODUCTS = "products";
    private static final String CATALOG_PAGE = "path.page.catalog";
    private static final String EDIT_PRODUCT_PAGE = "path.page.admin.edit";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String filename = null;
        int id = Integer.parseInt(request.getParameter(PARAM_NAME_PRODUCT_ID).trim());
        String name = request.getParameter(PARAM_NAME_NAME).trim();
        Double price = Double.valueOf(request.getParameter(PARAM_NAME_PRICE).trim());
        String description = request.getParameter(PARAM_NAME_DESCRIPTION).trim();
        String category = request.getParameter(PARAM_NAME_CATEGORY_NAME).trim();
        String image = request.getParameter(PARAM_NAME_IMAGE).trim();

        int amount = Integer.parseInt(request.getParameter(PARAM_NAME_AMOUNT).trim());
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
                    page = ConfigurationManager.getProperty(EDIT_PRODUCT_PAGE);
                    return page;
                }
                image = filename;
            }
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (ServletException e) {
            LOGGER.error(e);
        }

        Product product = new Product(id, name, price, description, category, image, amount);
        Validator<Product> validator = new ProductValidator();
        String validationProductResult = validator.isValid(product);

        if (validationProductResult == null) {
            ProductService service = ProductServiceImpl.getInstance();
            try {
                service.update(product);
                List<Product> products = service.allProducts();
                if (!products.isEmpty()) {
                    request.getSession().setAttribute(PRODUCTS, products);
                    page = ConfigurationManager.getProperty(CATALOG_PAGE);
                    request.getSession().setAttribute(URL, page);
                } else {
                    page = ConfigurationManager.getProperty(CATALOG_PAGE);
                    request.setAttribute(MESSAGE, MessageManager.HAVE_NOT_PRODUCTS);
                }
            } catch (ServiceException e) {
                LOGGER.error("Error during editing product: " + e);
                request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
                page = ConfigurationManager.getProperty(ERROR_PAGE);
            }
        } else {
            request.setAttribute(MESSAGE, validationProductResult);
            page = ConfigurationManager.getProperty(EDIT_PRODUCT_PAGE);
        }
        return page;
    }
}
