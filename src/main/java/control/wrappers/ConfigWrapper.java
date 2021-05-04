package control.wrappers;

import control.Job;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigWrapper {
    private static final Logger logger = Logger.getLogger(ConfigWrapper.class.getName());
    private String configReload;
    private int checkIntervals, checkConfigReload, executorThreads, scheduledThreads;
    private GeneralDownloadWrapper download;
    private DatabaseWrapper database;
    private Set<JobWrapper> jobs;

    public void setConfigReload(String configReload) {
        this.configReload = configReload;
    }

    /**
     * Gets the check interval in seconds
     *
     * @return check interval in seconds, 0 when empty
     */
    public int getCheckIntervals() {
        return checkIntervals;
    }

    public void setCheckIntervals(int checkIntervals) {
        this.checkIntervals = checkIntervals;
    }

    public DatabaseWrapper getDb() {
        return database;
    }

    public void setDatabase(DatabaseWrapper database) {
        this.database = database;
    }

    /**
     * Gets the number of executor threads
     *
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
     *
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
     *
     * @return true only when automatic config reload is required
     */
    public boolean isAutomaticReload() {
        return "yes".equalsIgnoreCase(configReload);
    }

    /**
     * Gets the GeneralDownloadWrapper
     *
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
     *
     * @return Set of Jobs or empty Set
     */
    public Set<Job> getJobs() {
        Set<Job> set = new HashSet<>();
        for (JobWrapper job : jobs) {
            try {
                set.add(job.toJob());
            } catch (IllegalArgumentException ex) {
                logger.log(Level.INFO, "Unable to get create a new Job", ex);
            }
        }
        return set;
    }

    public void setJobs(Set<JobWrapper> jobs) {
        this.jobs = jobs;
    }

    public int getCheckConfigReload() {
        return checkConfigReload;
    }

    public void setCheckConfigReload(int checkConfigReload) {
        this.checkConfigReload = checkConfigReload;
    }

}