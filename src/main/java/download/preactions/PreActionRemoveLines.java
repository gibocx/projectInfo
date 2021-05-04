package download.preactions;

import control.wrappers.PreActionWrapper;
import utility.Contains;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
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
    public byte[] compute(byte[] data) {
        if (linesToRemove.length == 0) {
            logger.fine("No lines to remove!");
            return data;
        }

        try (BufferedReader reader = new BufferedReader(new StringReader(new String(data, StandardCharsets.UTF_8)))) {
            StringBuilder builder = new StringBuilder();
            String lineSeparator = System.getProperty("line.separator");

            String currentLine;
            int line = -1;

            while ((currentLine = reader.readLine()) != null) {
                line++;
                if (Contains.contains(line, linesToRemove)) {
                    continue;
                }
                builder.append(currentLine).append(lineSeparator);
            }
            reader.close();

            return builder.toString().getBytes(StandardCharsets.UTF_8);
        } catch (IOException ex) {
            return new byte[0];
        }
    }

    protected void setLinesToRemove(int[] linesToRemove) {
        this.linesToRemove = linesToRemove;
    }
}
