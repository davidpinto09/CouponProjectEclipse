package couponSystem.jdbc.connectionPool;

import couponSystem.exceptions.CouponSystemException;

import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConnectionPool {


    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/coupon_system?serverTimezone=Israel";
    public static final String DATABASE_USER = "DPinto";
    public static final String DATABASE_PASSWORD = "Poly081090$";
    private static final int TOTAL_CONNECTIONS = 10;
    private static ConnectionPool connectionPoolInstance = null;
    private final Set<Connection> connections = new HashSet<>();
    private boolean isConnectionPoolOpen;

    /**
     * Private constructor for singleton class that add connections to the connectionPool Collection
     */
    private ConnectionPool() throws SQLException {


        for (int i = 0; i < TOTAL_CONNECTIONS; i++) {

            this.connections.add(DriverManager.getConnection(getDatabaseUrl(), getDatabaseUser(), getDatabasePassword()));

        }
        isConnectionPoolOpen = true;
        if (this.connections.size() == TOTAL_CONNECTIONS) {
            System.out.println("ConnectionPool is up");
        }

    }

    /**
     * @return object instance of connectionPool class
     */
    public static ConnectionPool getConnectionPoolInstance() throws CouponSystemException {
        if (connectionPoolInstance == null) {

            try {
                connectionPoolInstance = new ConnectionPool();
            } catch (SQLException sqlException) {
                throw new CouponSystemException("Initialization of ConnectionPool failed", sqlException);
            }
        }

        return connectionPoolInstance;
    }

    public static String getDatabaseUrl() {
        return DATABASE_URL;
    }

    public static String getDatabaseUser() {
        return DATABASE_USER;
    }

    public static String getDatabasePassword() {
        return DATABASE_PASSWORD;
    }

    public static int getTotalConnections() {
        return TOTAL_CONNECTIONS;
    }

    /**
     * @return one connection from the Collection of connections in the connectionPool
     */
    public synchronized Connection getConnection() throws CouponSystemException {
        if (!isConnectionPoolOpen) {
            throw new CouponSystemException("Getting connection failed - connectionPool is closed");
        } else {
            while (this.connections.isEmpty()) {

                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new CouponSystemException("Getting connection failed - wait was interrupted", e);
                }
            }
            Iterator<Connection> connectionIterator = this.connections.iterator();
            Connection connection = connectionIterator.next();
            connectionIterator.remove();
            return connection;
        }
    }

    /**
     * Restores the used connection back to the Collection connections in collectionPool
     *
     * @param connectionToRestore connection in use that returns to the list
     */
    public synchronized void restoreConnection(Connection connectionToRestore) {

        this.connections.add(connectionToRestore);
        notify();


    }

    /**
     * Closes all the open connections
     */
    public synchronized void closeAllConnections() throws CouponSystemException {
        isConnectionPoolOpen = false;
        while (this.connections.size() < TOTAL_CONNECTIONS) {
            try {
                wait();
            } catch (InterruptedException sqlException) {
                sqlException.printStackTrace();
            }
            Iterator<Connection> connectionIterator = this.connections.iterator();
            while (connectionIterator.hasNext()) {
                Connection connection = connectionIterator.next();
                try {
                    connection.close();
                    connectionIterator.remove();
                } catch (SQLException sqlException) {
                    throw new CouponSystemException("closing connections failed", sqlException);
                }
            }
            System.out.println("ConnectionPool is down");

        }
    }

    public Set<Connection> getConnections() {
        return connections;
    }
}
