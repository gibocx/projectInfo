package db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import utility.Concat;
import utility.Contains;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect {
    private static final Logger logger = Logger.getGlobal();
    private static ComboPooledDataSource source;

    public static void start(Map<String, String> data) {
        String[] reqArgs = new String[]{"jdbcurl", "user", "password"};
        if (!Contains.containsAll(data, reqArgs)) {
            logger.info("Provided map does at least not contain one of the following values "
                    + Concat.concat(reqArgs,null,";"));

            stop();
            return;
        }

        try {
            ComboPooledDataSource tmp = new ComboPooledDataSource();
            tmp.setJdbcUrl(data.get("jdbcurl"));
            tmp.setUser(data.get("user"));
            tmp.setPassword(data.get("password"));
            tmp.setDriverClass("com.mysql.cj.jdbc.Driver");

            // Optional Settings
            tmp.setInitialPoolSize(3);
            tmp.setMinPoolSize(3);
            tmp.setAcquireIncrement(1);
            tmp.setMaxPoolSize(30);

            synchronized (Connect.class) {
                source = tmp;
            }

            logger.info("Successfully set new Datasource!");
        } catch(PropertyVetoException e) {
            logger.log(Level.INFO,"YES",e);
        }
    }

    public static void stop() {
        synchronized (Connect.class) {
            if(source != null) {
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
            logger.warning("unable to get connection : " + ex.getMessage() + " " + ex.getSQLState());
        }

        return null;
    }

    public static void closeQuietly(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            logger.info("unable to close connection " + ex.getMessage() + " " + ex.getSQLState());
        }
    }
}