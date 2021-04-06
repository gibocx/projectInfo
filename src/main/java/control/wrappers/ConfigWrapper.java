package control.wrappers;

import control.Job;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigWrapper {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private String configReload;
    private int checkIntervals;
    private int checkConfigReload;
    private int executorThreads;
    private int scheduledThreads;
    private Map<String, String> db;
    private GeneralDownloadWrapper download;
    private List<JobWrapper> jobs;

    public void setConfigReload(String configReload) {
        this.configReload = configReload;
    }

    /**
     * Gets the check interval in seconds
     * @return check interval in seconds, 0 when empty
     */
    public int getCheckIntervals() {
        return checkIntervals;
    }

    public void setCheckIntervals(int checkIntervals) {
        this.checkIntervals = checkIntervals;
    }

    public void setDb(Map<String, String> db) {
        this.db = db;
    }

    /**
     * Gets the number of executor threads
     * @return number of executor threads, 0 when empty
     */
    public int getExecutorThreads() {
        return executorThreads;
    }

    public void setExecutorThreads(int numThreads) {
        this.executorThreads = numThreads;
    }

    /**
     * Gets the number of scheduled executor threads
     * @return number of scheduled executor threads, 0 when empty
     */
    public int getScheduledThreads() {
        return scheduledThreads;
    }

    public void setScheduledThreads(int numThreads) {
        scheduledThreads = numThreads;
    }

    /**
     * Returns true only when configReload matches "yes" or "YES"
     * @return true only when automactic config reload is required
     */
    public boolean isAutomaticReload() {
        if(configReload == null)
            return false;

        return configReload.equalsIgnoreCase("yes");
    }

    /**
     * Gets the GeneralDownloadWrapper
     * @return general download config or null
     */
    public GeneralDownloadWrapper getDownload() {
        return this.download;
    }

    public void setDownload(GeneralDownloadWrapper download) {
        this.download = download;
    }

    /**
     * Constructs a Set of all configured Jobs
     * @return Set of Jobs or empty Set
     */
    public Set<Job> getJobs() {
        Set<Job> set = new HashSet<>();
        for (JobWrapper job : jobs) {
            try {
                set.add(job.toJob());
            } catch (IllegalArgumentException ex) {
                logger.log(Level.INFO, "IllegalArgumentException : ", ex);
            }
        }
        return set;
    }

    public void setJobs(List<JobWrapper> jobs) {
        this.jobs = jobs;
    }

    public int getCheckConfigReload() {
        return checkConfigReload;
    }

    public void setCheckConfigReload(int checkConfigReload) {
        this.checkConfigReload = checkConfigReload;
    }

}