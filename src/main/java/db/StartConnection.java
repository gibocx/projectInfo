package db;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class StartConnection {
    private static final Logger logger = Logger.getGlobal();
    private static MysqlDataSource source;

    public static void start() {
        try {
            source = new MysqlConnectionPoolDataSource();
            /*

            source.setServerTimezone();*/

            source.setCharacterEncoding(StandardCharsets.UTF_8.name());
            source.setAllowMultiQueries(true);
            source.setCacheCallableStmts(true);

            System.out.println("Connected : " + source.getServerName());
        } catch (SQLException ex) {
            System.out.println("Problems regarding DataSource: " + ex.getMessage() + " " + ex.getSQLState());
        } finally {

        }
    }

    public static void setUser(String user) {
        source.setUser(user);
    }

    public static void setPassword(String password) {
        source.setPassword(password);
    }

    public static void setServer(String name) {
        source.setServerName(name);
    }

    public static void setPort(int port) {
        source.setPort(port);
    }

    /**
     * Simply gets an Connection
     *
     * @return Connection
     */
    public static Connection getConnection() {
        if (source == null)
            start();

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
     */
    public static Connection getConnection(String schema) {
        if (schema == null) {
            return getConnection();
        }

        if (source == null) {
            start();
        }

        try {
            Connection con = source.getConnection();
            con.setSchema(schema);
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
