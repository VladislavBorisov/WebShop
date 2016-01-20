package com.web.shop.resource;

import java.util.ResourceBundle;

public class ConfigurationManager {
    private final static String PROPERTY_FILE = "config";
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(PROPERTY_FILE);

    private ConfigurationManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}