package download.preactions;

import utility.Contains;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

class PreActionRemoveLines implements PreAction {
    private int[] linesToRemove;

    /**
     * First line is 0
     *
     * @param linesToRemove
     */
    PreActionRemoveLines(int[] linesToRemove) {
        this.linesToRemove = linesToRemove;
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