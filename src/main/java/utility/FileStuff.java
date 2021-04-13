package utility;

import java.io.*;
import java.util.logging.Logger;

public class FileStuff {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static boolean isValid(File file) {
        if (file != null) {
            return file.exists() && file.isFile();
        } else {
            logger.fine("Supplied File is null!!");
        }
        return false;
    }

    public static boolean isValid(String file) {
        if (file != null) {
            File tmp = new File(file);
            return isValid(tmp);
        }

        return false;
    }

    /**
     * Tries to create the given file when not already exists
     *
     * @param file File to create
     * @return true only when created successful
     */
    public static boolean createFile(File file) {
        if (!isValid(file) && file != null) {
            try {
                return file.createNewFile();
            } catch (IOException | SecurityException ex) {
                logger.fine("Could not create file " + file.getName() + " Exception " + ex.getMessage());
            }
        } else {
            logger.fine("File already exists or null!");
        }
        return false;
    }

    /**
     * Tries to create the given file when not already exists
     *
     * @param path Path to create
     * @return true only when created successful
     */
    public static boolean createFile(String path) {
        if (path != null) {
            File file = new File(path);
            return createFile(file);
        }

        return false;
    }

    /**
     * Deletes the given File
     *
     * @param file File to delete
     * @return true only if successfully deleted
     */
    public static boolean delete(File file) {
        if (isValid(file)) {
            return file.delete();
        }
        return false;
    }

    /**
     * Deletes the given path
     *
     * @param path file path to delete
     * @return true only if successfully deleted
     */
    public static boolean delete(String path) {
        if (path != null) {
            File file = new File(path);
            return delete(file);
        }
        return false;
    }

}
