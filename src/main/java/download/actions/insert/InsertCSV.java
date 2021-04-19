package download.actions.insert;


import db.RunQuery;
import download.Category;
import utility.Concat;
import utility.Readers;

import java.util.logging.Logger;

class InsertCSV implements InsertDataType {
    // matches the character before the regex only when not enclosed in "
    private static final String REGEX = "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    private static final Logger logger = Logger.getGlobal();
    private String commaDelimiter = ",";
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

        if(in.get("delimiter") != null) {
            String delimiter = in.get("delimiter");

            if(delimiter.length() == 1) {
                commaDelimiter = delimiter;
                logger.fine(() -> "Set CSV comma delimiter to " + delimiter);
            } else {
                logger.info(() -> "CSV delimiter has to be one character long! Delimiter was " + delimiter);
            }
        }

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
        Readers.readLineByLine(str, (line) -> run.add(computeValues(line)));
        return run.execute();
    }

    /**
     * Extract the desired columns and returns them in the desired order
     *
     * @param line one line of the csv file
     * @return desired String array of the columns
     */
    private String[] computeValues(String line) {
        String[] columns = line.split(commaDelimiter + REGEX);
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
