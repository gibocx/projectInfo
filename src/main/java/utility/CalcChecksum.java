package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

import static utility.FileStuff.isValid;

public class CalcChecksum {
    static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final int BUFFER_SIZE = 4096;

    /**
     * Computes the checksum of a byte array
     *
     * @param bytes Byte[] to compute the checksum of
     * @return checksum as long, 0 when null!
     */
    public static long checksum(byte[] bytes) {
        if (bytes != null) {
            Checksum sum = new Adler32();
            sum.update(bytes, 0, bytes.length);
            return sum.getValue();
        } else {
            return 0;
        }
    }

    /**
     * Computes the checksum of a String
     *
     * @param str String to compute the checksum of
     * @return checksum as long, 0 when null!
     */
    public static long checksum(String str) {
        if (str != null) {
            return checksum(str.getBytes(StandardCharsets.UTF_8));
        } else {
            return 0;
        }
    }

    /**
     * Computes the checksum of a File
     *
     * @param file File to compute the checksum of
     * @return checksum as long, 0 when file is null!
     * @throws IOException when unable to read file
     */
    public static long checksum(File file) throws IOException {
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
                throw new IOException(ex.getCause());
            }
            return sum.getValue();
        }
        return 0;
    }

}
