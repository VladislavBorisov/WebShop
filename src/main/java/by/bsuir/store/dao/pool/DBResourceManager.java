package by.bsuir.store.dao.pool;

import java.util.ResourceBundle;

/**
 * Provides link to a db.properties file. Contains methods to work with it.
 */
public final class DBResourceManager {

    private DBResourceManager() {
    }

    private final static String PROPERTY_FILE = "db";
    private static final ResourceBundle bundle = ResourceBundle.getBundle(PROPERTY_FILE);

    /**
     * Gets a string for the given key from db.properties file.
     *
     * @param key the key for the desired string
     * @return the string for the given key
     */
    public static String getValue(String key) {
        return bundle.getString(key);
    }
}
