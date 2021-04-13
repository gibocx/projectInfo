package utility;

import java.io.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Readers {
    private static final Logger logger = Logger.getGlobal();
    public static void readLineByLine(File file, Consumer<String> func) {
        if(!FileStuff.isValid(file)) {
            return;
        }

        try {
            readLineByLine(new FileReader(file), func);
        } catch (FileNotFoundException ex) {
            logger.log(Level.FINE, "File " + file.getName() + " could not be found!",ex);
        }
    }

    private static void readLineByLine(Reader r, Consumer<String> func) {
        try (BufferedReader br = new BufferedReader(r)) {
            String line;

            while ((line = br.readLine()) != null) {
                func.accept(line);
            }
        } catch (IOException ex) {
            logger.log(Level.FINE,"IOException",ex);
        }
    }

    public static void readLineByLine(String str, Consumer<String> func) {
        if(str == null || str.isEmpty()) {
            return;
        }

        readLineByLine(new StringReader(str),func);
    }
}
