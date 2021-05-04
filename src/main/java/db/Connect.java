package db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import control.wrappers.DatabaseWrapper;
import utility.Concat;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect {
    private static final Logger logger = Logger.getLogger(Connect.class.getName());
    private static ComboPooledDataSource source;

    public static void start(DatabaseWrapper db) {
        String[] reqArgs = new String[]{"jdbcurl", "user", "password"};
        if (!db.isValid()) {
            logger.info("Provided map does at least not contain one of the following values "
                    + Concat.concat(reqArgs, null, ";"));

            stop();
            return;
        }

        try {
            ComboPooledDataSource tmp = new ComboPooledDataSource();
            tmp.setJdbcUrl(db.getJdbcUrl());
            tmp.setUser(db.getUser());
            tmp.setPassword(db.getPassword());
            tmp.setDriverClass("com.mysql.cj.jdbc.Driver");

            // Optional Settings
            tmp.setInitialPoolSize(3);
            tmp.setMinPoolSize(3);
            tmp.setAcquireIncrement(1);
            tmp.setMaxPoolSize(30);

            synchronized (Connect.class) {
                source = tmp;
            }

            if (isAvailable()) {
                logger.info("Successfully set new Datasource!");
            } else {
                stop();
            }

        } catch (PropertyVetoException ex) {
            logger.log(Level.INFO, "YES", ex);
        }
    }

    /**
     * Checks the connection
     *
     * @return true when connection is connected
     */
    public static boolean isAvailable() {
        try {
            source.getConnection().createStatement().execute("SELECT 1;");
            return true;
        } catch (SQLException | NullPointerException ex) {
            logger.log(Level.FINE, "Problems with connection!", ex);
            return false;
        }
    }

    public static boolean isActive(Connection con) {
        try {
            con.createStatement().execute("SELECT 1");
            return true;
        } catch (SQLException | NullPointerException ex) {
            logger.log(Level.FINE, "Problems with connection!", ex);
            return false;
        }
    }

    public static void stop() {
        synchronized (Connect.class) {
            if (source != null) {
                source.close();
                source = null;
            }
        }
    }

    /**
     * Simply gets an Connection
     *
     * @return Connection
     */
    public static Connection getConnection() {
        if (source == null) {
            throw new IllegalStateException("No connection has yet been established!");
        }

        try {
            return source.getConnection();
        } catch (SQLException ex) {
            logger.warning("unable to get connection : " + ex.getMessage() + " " + ex.getSQLState());
        }

        return null;
    }

    /**
     * Simply gets an Connection with the specified schema
     *
     * @return Connection
     * @throws IllegalStateException when no connection has yet been established
     */
    public static Connection getConnection(String schema) {
        if (schema == null) {
            return getConnection();
        }

        if (source == null) {
            throw new IllegalStateException("No connection has yet been established!");
        }

        try {
            Connection con = source.getConnection();
            con.setSchema(schema);
            con.setCatalog(schema);
            return con;
        } catch (SQLException ex) {
            logger.warning("unable to get connection : " + ex.getMessage());
        }

        return null;
    }

    public static void closeQuietly(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            logger.info("unable to close connection " + ex.getMessage());
        }
    }
}
