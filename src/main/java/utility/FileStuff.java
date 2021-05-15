package utility;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class FileStuff {
    private static final Logger logger = Logger.getLogger(FileStuff.class.getName());

    /**
     * Checks the given File for validity.
     * @param file File to Check
     * @return only true when file is valid and a file
     */
    public static boolean isValid(final File file) {
        if (file != null) {
            return (file.exists() && file.isFile());
        }

        return false;
    }

    /**
     * Checks the given filePath for validity.
     * @param path
     * @return only true when file is valid and a file
     */
    public static boolean isValid(final String path) {
        if (path != null) {
            File tmp = new File(path);
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
    public static boolean createFile(final File file) {
        if (file != null && !isValid(file)) {
            try {
                return file.createNewFile();
            } catch (IOException | SecurityException ex) {
                logger.fine("Could not create file " + file.getName() + " Exception " + ex.getMessage());
            }
        }

        return false;
    }

    /**
     * Tries to create the given file when not already exists
     *
     * @param path Path to create
     * @return true only when created successful
     */
    public static boolean createFile(final String path) {
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
    public static boolean delete(final File file) {
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
    public static boolean delete(final String path) {
        if (path != null) {
            File file = new File(path);
            return delete(file);
        }
        return false;
    }

}
