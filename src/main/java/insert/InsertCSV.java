package insert;


import db.RunQuery;
import download.Category;
import utility.Concat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.logging.Logger;

public class InsertCSV implements InsertDataType {
    public static final String COMMA_DELIMITER = ";";
    private static final Logger logger = Logger.getGlobal();
    private final RunQuery run;
    private final int numDesiredColumns;
    private final int[] desiredColumns;

    public InsertCSV(InsertInfo in, RunQuery run) {
        if (run == null) {
            throw new IllegalArgumentException("run can not be empty!");
        }

        if (in == null) {
            throw new IllegalArgumentException("in can not be empty!");
        }

        this.run = run;

        numDesiredColumns = getNumDesiredColumns(in);
        desiredColumns = new int[numDesiredColumns];

        populateDesiredColumns(in);
    }

    private void populateDesiredColumns(InsertInfo in) {
        String tmp = null;
        int i = 1;

        try {
            while ((tmp = in.get("argument" + i)) != null) {
                desiredColumns[i - 1] = Integer.parseInt(tmp.split("row")[1]);
                i++;
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Can not parse number from " + tmp
                    + "in argument" + i + "; " + tmp.split("row")[1] + " is not a valid number!");
        }
    }

    private int getNumDesiredColumns(InsertInfo in) {
        int i = 1;
        while (in.get("argument" + i) != null) {
            i++;
        }
        return i - 1;
    }

    public boolean insert(String str, Category category) {
        try (BufferedReader reader = new BufferedReader(new StringReader(str))) {
            String line = reader.readLine();
            while (line != null) {
                // do some magic with line extract interpreter ....
                run.add(computeValues(line.split(COMMA_DELIMITER)));
                line = reader.readLine();
            }

            run.execute();
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Extract the desired columns and returns them in the desired order
     *
     * @param columns String array of columns
     * @return desired String array of the columns
     */
    private String[] computeValues(String[] columns) {
        String[] values = new String[numDesiredColumns];

        if (columns.length + 1 < numDesiredColumns) {
            logger.fine("Number of desired columns does not match! Desired : " + numDesiredColumns
                    + " actual :" + (columns.length + 1) + ". From line \"" + Concat.concat(columns) + "\". Desired " +
                    "Columns are"+ System.lineSeparator() + Concat.concat(desiredColumns, "row: ", System.lineSeparator()));
            return new String[0];
        }

        for (int i = 0; i < numDesiredColumns; i++) {
            values[i] = columns[desiredColumns[i]];
        }

        return values;
    }

    public boolean finish() {
        return run.close();
    }
}
