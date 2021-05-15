package download.preactions;

import control.wrappers.PreActionWrapper;
import utility.Contains;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

class PreActionRemoveLines implements PreAction {
    private static final Logger logger = Logger.getLogger(PreActionRemoveLines.class.getName());
    private int[] linesToRemove;

    /**
     * First line is 0
     *
     * @param linesToRemove
     */
    PreActionRemoveLines(int[] linesToRemove) {
        this.linesToRemove = linesToRemove;
    }

    public PreActionRemoveLines(PreActionWrapper pre) {
        linesToRemove = pre.getRows();
    }

    public PreActionRemoveLines() {
    }

    /**
     * @param data byte array to remove specified lines from
     * @return byte[] without the specified lines
     */
    @Override
    public boolean compute(byte[] data) {
        if (linesToRemove.length == 0) {
            logger.fine("No lines to remove!");
            return false;
        }

        StringBuilder builder = new StringBuilder(data.length/2);
        String line = null;
        String lineSeparator = System.lineSeparator();
        int lineCnt = 0;

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));
            while((line = reader.readLine()) != null) {
                if(Contains.contains(lineCnt++,linesToRemove)) {
                    continue;
                }

                builder.append(line).append(lineSeparator);
            }

            data = builder.toString().getBytes(StandardCharsets.UTF_8);
            return true;
        } catch(IOException ex) {
            logger.log(Level.INFO, "Unable to remove lines!",ex);
        }
        return false;
    }

    protected void setLinesToRemove(int[] linesToRemove) {
        this.linesToRemove = linesToRemove;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreActionRemoveLines that = (PreActionRemoveLines) o;
        return Arrays.equals(linesToRemove, that.linesToRemove);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(linesToRemove);
    }
}
