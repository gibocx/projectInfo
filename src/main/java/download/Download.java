package download;

import org.apache.commons.text.StringEscapeUtils;
import utility.TimeDiff;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Download {
    private static final Logger logger = Logger.getLogger(Download.class.getName());
    private static final int STD_TIMEOUT = 5000;
    private static final int BUFFER_SIZE = 4096;
    private static int readTimeout = STD_TIMEOUT;
    private static int connectionTimeout = STD_TIMEOUT;

    private Download() {
        throw new IllegalArgumentException("Download Utility class!");
    }


    /**
     * Downloads the given url
     *
     * @param url         url to download
     * @param contentType content type of the accessed item
     * @return downloaded contents as byte[], empty array when problems occur
     */
    public static byte[] downloadByteArray(String url, String contentType) {
        logger.fine(() -> "Downloading url : " + url);

        try {
            TimeDiff time = new TimeDiff();
            HttpURLConnection connection = connectionSetProperties(url, contentType);
            InputStream is = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[BUFFER_SIZE];

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            is.close();
            connection.disconnect();

            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Downloaded url" + url + "; size " + buffer.size() + " bytes in " + time.chooseBest());
            }
            return buffer.toByteArray();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "IOException ", ex);
        }
        return new byte[0];
    }

    /**
     * Downloads the given url
     *
     * @param url         url to download
     * @param contentType content type of the accessed item
     * @return downloaded contents as UTF-8 String
     */
    public static String downloadString(String url, String contentType) {
        return replaceEscapes(contentType, new String(downloadByteArray(url, contentType), StandardCharsets.UTF_8));
    }

    private static HttpURLConnection connectionSetProperties(String url, String contentType) throws IOException {

        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", UserAgentPool.getUserAgent());
        con.setRequestProperty("Accept-Charset", "UTF-8");

        if (contentType != null) {
            con.setRequestProperty("Content-Type", contentType);
        } else {
            logger.info(() -> "No content type supplied for given url: " + url + "!");
        }

        con.setUseCaches(false);
        con.setDoOutput(true);
        con.setDoInput(true);

        con.setConnectTimeout(connectionTimeout);
        con.setReadTimeout(readTimeout);
        con.setInstanceFollowRedirects(true);
        con.connect();

        if (400 <= con.getResponseCode() && con.getResponseCode() <= 600) {
            logger.warning("Response code from " + url + "is " + con.getResponseCode());
            logger.info(() -> new BufferedReader(new InputStreamReader(con.getErrorStream()))
                    .lines().collect(Collectors.joining("\n")));

        }

        return con;
    }

    private static String replaceEscapes(String contentType, String str) {
        // content type can also be: text/html; charset=UTF-8
        contentType = contentType.split(";")[0];

        switch (contentType) {
            case "text/html":
                str = StringEscapeUtils.unescapeHtml4(str);
                break;
            case "application/json":
            case "application/octet-stream":
                break;
            default:
                logger.info("Unknown content type : " + contentType);
        }
        return str;
    }

    /**
     * Sets the read timeout
     *
     * @param timeout read timeout in milliseconds (default = 5000)
     */
    public static void setReadTimeout(int timeout) {
        if (timeout <= 0) {
            readTimeout = STD_TIMEOUT;
            logger.warning(() -> "Timeout set to default(" + STD_TIMEOUT + "); wrong timeout:" + timeout);
        }

        readTimeout = timeout;
        logger.config(() -> "Set download timeout to " + timeout + " ms");
    }

    /**
     * Sets the connection timeout
     *
     * @param timeout connection timeout in milliseconds (default = 5000)
     */
    public static void setConnectionTimeout(int timeout) {
        if (timeout <= 0) {
            connectionTimeout = STD_TIMEOUT;
            logger.warning(() -> "Timeout set to default(" + STD_TIMEOUT + "); wrong timeout:" + timeout);
        }

        connectionTimeout = timeout;
        logger.config(() -> "Set connection timeout to " + timeout + " ms");
    }
}
