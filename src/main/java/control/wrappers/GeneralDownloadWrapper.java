package control.wrappers;

import java.io.File;
import java.util.*;

public class GeneralDownloadWrapper {
    private int connectionTimeout, readTimeout;
    private String userAgentFile, userAgentFileMode;
    private int userAgentReloadPeriod;
    private Set<String> userAgents;

    /**
     * Return the connectionTimeout specified in the config file
     *
     * @return specified connectionTimeout, 0 undefined
     */
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * Return the readTimeout specified in the config file
     *
     * @return specified readTimeout, 0 undefined
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * Returns the userAgentFile as File Object specified in the config file
     *
     * @return File object or null when not found
     */
    public File getUserAgentFile() {
        if (userAgentFile != null) {
            return new File(userAgentFile);
        } else {
            return null;
        }
    }

    public void setUserAgentFile(String userAgentFile) {
        this.userAgentFile = userAgentFile;
    }

    /**
     * Returns the userAgentFileMode as String Object specified in the config file
     *
     * @return String or null when not found
     */
    public String getUserAgentFileMode() {
        return userAgentFileMode;
    }

    public void setUserAgentFileMode(String userAgentFileMode) {
        this.userAgentFileMode = userAgentFileMode.toUpperCase(StandardCharsets.UTF_8);
    }

    public Set<String> getUserAgents() {
        if (userAgents == null) {
            userAgents = new HashSet<>();
        }
        return userAgents;
    }

    public void setUserAgents(Set<String> userAgents) {
        userAgents.removeIf(Objects::isNull);
        this.userAgents = userAgents;
    }

    /**
     * Returns the userAgentReloadPeriod as int
     *
     * @return userAgentReloadPeriod, 0 as default
     */
    public int getUserAgentReloadPeriod() {
        return userAgentReloadPeriod;
    }

    public void setUserAgentReloadPeriod(int userAgentReloadPeriod) {
        this.userAgentReloadPeriod = userAgentReloadPeriod;
    }
}