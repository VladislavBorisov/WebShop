package by.bsuir.store.listener;

import by.bsuir.store.domain.Product;
import by.bsuir.store.service.ProductService;
import by.bsuir.store.service.ServiceException;
import by.bsuir.store.service.impl.ProductServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Iterator;
import java.util.Map;

public class BasketSessionListener implements HttpSessionListener {
    private static final Logger LOGGER = Logger.getLogger(BasketSessionListener.class);
    private static final String MAP_ID_BASKET_AMOUNT = "map";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        Map<Integer, Integer> map = (Map<Integer, Integer>) httpSessionEvent.getSession().getAttribute(MAP_ID_BASKET_AMOUNT);
        if (!map.isEmpty()){
            ProductService productService = ProductServiceImpl.getInstance();
            try {
                Iterator iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry pair = (Map.Entry) iterator.next();
                    Product product = productService.findById((Integer) pair.getKey());
                    int currentAmount = product.getAmount();
                    product.setAmount(currentAmount + (Integer) pair.getValue());
                    productService.update(product);
                }
            } catch (ServiceException e) {
                LOGGER.error("Error during cleaning basket" + e);
            }
        }
    }
}
