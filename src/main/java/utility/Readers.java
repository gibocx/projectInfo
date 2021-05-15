package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Readers {
    private static final Logger logger = Logger.getLogger(Readers.class.getName());

    /**
     * Reads the given file line by line by line as string
     * @param file file to read
     * @param func String consumer of the currently read line
     */
    public static void readLineByLine(File file, Consumer<String> func) {
        if(FileStuff.isValid(file)) {
            try {
                readLineByLine(new FileReader(file), func);
            } catch (FileNotFoundException ex) {
                logger.log(Level.FINE, "File " + file.getName() + " could not be found!", ex);
            }
        }
    }

    /**
     * Reads the given String line by line by line as string
     * @param str String to read
     * @param func String consumer of the currently read line
     */
    public static void readLineByLine(String str, Consumer<String> func) {
        if (str != null) {
            readLineByLine(new StringReader(str), func);
        }
    }

    private static void readLineByLine(Reader r, Consumer<String> func) {
        if (func != null) {
            try (BufferedReader br = new BufferedReader(r)) {
                String line;

                while ((line = br.readLine()) != null) {
                    func.accept(line);
                }
            } catch (IOException ex) {
                logger.log(Level.FINE, "IOException", ex);
            }
        }
    }
}
