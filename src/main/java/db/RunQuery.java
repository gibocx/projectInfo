package db;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class RunQuery {
    private static final int MAX_BATCH_SIZE = 100;
    private static final Logger logger = Logger.getGlobal();
    private final String sql;
    private final PreparedStatement statement;
    private final Connection con;
    private static final boolean CRUDE_SQL_CHECK = true;
    private int batchSize = 0;
    private final int parameterCount;

    public RunQuery(String sql, Connection con) {
        this.sql = sql;
        this.con = con;

        if(CRUDE_SQL_CHECK) {
            checkQuery();
        }

        try {
            con.setAutoCommit(false);

            if (isCallable()) {
                statement = con.prepareCall(sql);
            } else {
                statement = con.prepareStatement(sql);
            }

            ParameterMetaData params = statement.getParameterMetaData();
            parameterCount = params.getParameterCount();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    private void checkQuery() {
        String lowercase = sql.toLowerCase();

        String[] warnings = {" delete ", " alter ", " update ", " drop "};

        for(String warn : warnings) {
            if(lowercase.contains(warn)) {
                throw new IllegalArgumentException("SQL-Query : " + sql + " includes invalid keyword " + warn);
            }
        }
    }

    private boolean isCallable() {
        return sql.contains("call ");
    }

    public boolean add(String[] values) {
        try {
            int args;

            for (args = 1; args < (values.length + 1); args++) {
                if (values[args] != null) {
                    statement.setString(args, values[args]);
                } else {
                    logger.warning("This function requires at least one value in the array!");
                    return false;
                }
            }

            if (parameterCount == args) {
                statement.addBatch();
                batchSize++;
            } else {
                logger.info("No matching Parameter sizes requested arguments :" + parameterCount
                        + " actual arguments: " + args);
            }

            if (batchSize >= MAX_BATCH_SIZE) {
                complete();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean execute() throws SQLException {
        statement.executeBatch();
        con.commit();
        return true;
    }

    public boolean complete() {
        try {
            execute();
            statement.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
