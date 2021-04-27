package db;

import utility.Concat;
import utility.Contains;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunQuery {
    public static final int MAX_BATCH_SIZE = 50;
    private static final Logger logger = Logger.getGlobal();
    private static final boolean CRUDE_SQL_CHECK = true;
    private final Connection con;
    private final PreparedStatement statement;
    private final int parameterCount;
    private int batchSize = 0;

    public RunQuery(String sql, String schema) {
        this.con = Connect.getConnection(schema);

        if (CRUDE_SQL_CHECK) {
            checkQuery(sql);
        }

        try {
            con.setAutoCommit(false);

            if (isCallable(sql)) {
                statement = con.prepareCall(sql);
            } else {
                statement = con.prepareStatement(sql);
            }

            parameterCount = statement.getParameterMetaData().getParameterCount();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    private void checkQuery(String sql) {
        String[] warnings = {"delete ", "alter ", "update ", "drop "};

        if (Contains.containsSubStrings(sql, warnings)) {
            throw new IllegalArgumentException("SQL-Query : " + sql + " includes at least one invalid keyword "
                    + Concat.concat(warnings));
        }
    }

    private boolean isCallable(String sql) {
        return sql.contains("call ");
    }

    public boolean add(String[] values) {
        try {
            int args = 0;

            if (parameterCount == values.length) {
                for (args = 0; args < values.length; args++) {
                    if (values[args] != null) {
                        statement.setString(args+1, values[args]);
                    } else {
                        logger.warning("This function requires at least one value in the array!");
                        return false;
                    }
                }

                statement.addBatch();

                if (++batchSize >= MAX_BATCH_SIZE) {
                    execute();
                    batchSize = 0;
                }
            } else {
                logger.fine("No matching Parameter sizes requested arguments :" + parameterCount
                        + " actual arguments: " + args+1);
            }
        } catch (SQLException ex) {
            logger.log(Level.FINE,"",ex);
            return false;
        }

        return true;
    }

    public boolean execute() {
        try {
            statement.executeBatch();
            con.commit();
        } catch(SQLException ex) {
            logger.log(Level.FINE,"Unable to execute and commit batch",ex);
            return false;
        }

        return true;
    }

    public boolean close() {
            execute();
        try {
            statement.close();
        } catch (SQLException ex) {
            logger.log(Level.FINE,"Unable to close statement",ex);
            return false;
        }

        return true;
    }
}
