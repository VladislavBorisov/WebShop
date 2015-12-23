package by.bsuir.store.dao.pool;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * <p>This class designed for creating connection pool</p>
 *
 */
public final class DBConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(DBConnectionPool.class);
    private static final String DRIVER = DBResourceManager.getValue(DBPropertyName.DB_DRIVER);
    private static final String URL = DBResourceManager.getValue(DBPropertyName.DB_URL);
    private static final String LOGIN = DBResourceManager.getValue(DBPropertyName.DB_USER);
    private static final String PASS = DBResourceManager.getValue(DBPropertyName.DB_PASSWORD);
    private static final int SIZE = Integer.parseInt(DBResourceManager.getValue(DBPropertyName.DB_POOLSIZE));
    private static final String ERROR_INFO = "Connection not in the usedConnections";
    private final BlockingQueue<ConnectionWrapper> availableConnections;//
    private final BlockingQueue<ConnectionWrapper> usedConnections;
    private static DBConnectionPool dbConnectionPool;
    private static final Lock lock = new ReentrantLock();
    private String url;
    private String password;
    private String user;

    public void initializeAvailableConnection() throws ConnectionPoolException{
        for (int i = 0; i < SIZE; i++) {
            Connection connection;
            try {
                connection = getConnection();
            } catch (ConnectionPoolException e) {
                throw new ConnectionPoolException(e);
            }
            ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
            availableConnections.add(connectionWrapper);
        }
    }

    private DBConnectionPool(String url, String user, String password) throws ConnectionPoolException {
        try {
            Class.forName(DRIVER);
            this.url = url;
            this.password = password;
            this.user = user;
            availableConnections = new ArrayBlockingQueue<>(SIZE);
            usedConnections = new ArrayBlockingQueue<>(SIZE);
        } catch (Exception e) {
            throw new ConnectionPoolException(e);
        }
    }

    public static DBConnectionPool getInstance() throws ConnectionPoolException {
        try {
            lock.lock();
            if (dbConnectionPool == null) {
                dbConnectionPool = new DBConnectionPool(URL, LOGIN, PASS);
            }
        } finally {
            lock.unlock();
        }
        return dbConnectionPool;

    }

    private Connection getConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
        return connection;
    }

    /**
     * <p>This method takes connection from availableConnections and put it into usedConnections,and
     * then return this connection</p>
     *
     * @return newConnection returns the connection for working with it
     * @throws ConnectionPoolException if he catches InterruptedException
     */

    public Connection takeConnection() throws ConnectionPoolException {
        ConnectionWrapper newConnection;
        try {
            while (availableConnections.size() == 0) {
                Thread.sleep(5);
            }
            newConnection = availableConnections.take();
            usedConnections.put(newConnection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e);
        }
        return newConnection;
    }

    /**
     * <p>This method returns connection back in availableConnections</p>
     *
     * @throws ConnectionPoolException if he catches InterruptedException
     */
    public void closeConnection(Connection c) throws ConnectionPoolException {
        try {
            if (c != null) {
                if (usedConnections.remove(c)) {
                    availableConnections.put((ConnectionWrapper) c);
                } else {
                    throw new ConnectionPoolException(ERROR_INFO);
                }
            }
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e);

        }
    }

    public void destroyConnectionPool() throws ConnectionPoolException {
        try {
            closeConnectionQueue(availableConnections);
        } catch (ConnectionPoolException e) {
            throw new ConnectionPoolException("Can not correctly destroy connection pool.");
        } finally {
            closeConnectionQueue(usedConnections);
        }
    }

    private void closeConnectionQueue(BlockingQueue<ConnectionWrapper> queue) throws ConnectionPoolException {
        for (Connection connection : queue) {
            try {
                if (connection != null) {
                    if (!connection.getAutoCommit()) {
                        connection.commit();
                    }
                    ((ConnectionWrapper) connection).closeWrapperConnection();
                }
            } catch (SQLException e) {
                throw new ConnectionPoolException("Can not close connection queue.");
            }
        }
    }

    private class ConnectionWrapper implements Connection {

        private Connection connection;

        public ConnectionWrapper(Connection connection) {
            this.connection = connection;
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException("Can not close closed exception.");
            }
            try {
                closeConnection(connection);
            } catch (ConnectionPoolException e) {
                LOGGER.error(e);
            }
        }

        private void closeWrapperConnection() throws SQLException {
            this.connection.close();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            connection.rollback();
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            connection.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            connection.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            connection.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }
    }


}
