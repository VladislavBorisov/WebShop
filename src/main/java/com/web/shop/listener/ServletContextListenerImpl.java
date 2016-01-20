package com.web.shop.listener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.web.shop.dao.pool.ConnectionPoolException;
import com.web.shop.dao.pool.DBConnectionPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class ServletContextListenerImpl implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(ServletContextListenerImpl.class);
    private static DBConnectionPool dbConnectionPool;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        if (log4jConfigFile == null) {
            context.log("Parameter to initialize log4j does not exist");
        } else {
            String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
            PropertyConfigurator.configure(fullPath);
            LOGGER.info("Logger initialized successfully ");
        }

        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            dbConnectionPool.initializeAvailableConnection();
            LOGGER.info("Connection pool initialized successfully");
        } catch (ConnectionPoolException e) {
            throw new ConnectionPoolNotInitializedException("Connection cannot be initialized", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            dbConnectionPool.destroyConnectionPool();
            LOGGER.info("Connection pool destroyed successfully");
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool not correctly ", e);
        }
    }
}
