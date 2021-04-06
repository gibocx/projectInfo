package insert;


import db.RunQuery;
import download.Category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class InsertCSV implements InsertDataType {
    public static final String COMMA_DELIMITER = ",";
    private final RunQuery run;

    private final int numDesiredColumns;
    private final int[] desiredColumns;

    public InsertCSV(InsertInfo in, RunQuery run) {
        if(run == null) {
            throw new IllegalArgumentException("run can not be empty!");
        }

        if(in == null) {
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

            run.complete();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Extract the desired columns and returns them in the desired order
     *
     * @param columns String array of columns
     * @return desired String array of the columns
     */
    private String[] computeValues(String[] columns) {
        String[] values = new String[numDesiredColumns];

        for (int i = 0; i < numDesiredColumns; i++) {
            values[i] = columns[desiredColumns[i]];
        }

        return values;
    }
}
