package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

import static utility.FileStuff.isValid;

public class CalcChecksum {
    static final Logger logger = Logger.getLogger(CalcChecksum.class.getName());
    private static final int BUFFER_SIZE = 4096;

    /**
     * Computes the checksum of a byte array
     *
     * @param bytes Byte[] to compute the checksum of
     * @return checksum as long
     */
    public static long checksum(byte[] bytes) {
        if (bytes == null) {
            bytes = new byte[0];
        }

        Checksum sum = new Adler32();
        sum.update(bytes, 0, bytes.length);
        return sum.getValue();
    }

    /**
     * Computes the checksum of a String
     *
     * @param str String to compute the checksum of
     * @return checksum as long
     */
    public static long checksum(String str) {
        if (str == null) {
            str = "";
        }

        return checksum(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Computes the checksum of a File
     * When an Exception occurs or the filename is invalid the same
     * arbitrary value is returned.
     *
     * @param file File to compute the checksum of
     * @return checksum as long
     */
    public static long checksum(File file) {
        if (isValid(file)) {
            Checksum sum = new Adler32();
            byte[] buffer = new byte[BUFFER_SIZE];

            try (InputStream input = new FileInputStream(file)) {
                int bytesRead = input.read(buffer);
                while (bytesRead >= 0) {
                    sum.update(buffer, 0, bytesRead);
                    bytesRead = input.read(buffer);
                }
            } catch (IOException ex) {
                logger.log(Level.FINE, "IOException!", ex);
                return checksum((String) null);
            }
            return sum.getValue();
        }

        return checksum((String) null);
    }
}
