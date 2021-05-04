package control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import control.executorhandler.ExecutorHandler;
import control.status.CheckForDownload;
import control.status.CheckForReload;
import control.status.HealthCheck;
import control.status.UpSince;
import control.wrappers.ConfigWrapper;
import control.wrappers.GeneralDownloadWrapper;
import db.Connect;
import download.Download;
import download.UserAgentPool;
import utility.CalcChecksum;
import utility.TimeDiff;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses the YAML config file located at ./projectInfo.yaml and
 * sets all the configured properties.
 */
public class ReadConfig {
    private static final Logger logger = Logger.getLogger(ReadConfig.class.getName());
    private static final File CONFIG_FILE = new File("./projectInfo.yaml".replace("/", File.separator));
    private static boolean automaticReload;
    private static long configFileChecksum;

    /**
     * Forces the config to be reloaded
     *
     * @return true when successful
     */
    public static boolean forceRead() {
        TimeDiff time = new TimeDiff();

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            ConfigWrapper config = mapper.readValue(new File("projectInfo.yaml"), ConfigWrapper.class);

            Connect.start(config.getDb());

            ExecutorHandler.startExecutor(config.getExecutorThreads());
            ExecutorHandler.startScheduledExecutor(config.getScheduledThreads());

            if (config.isAutomaticReload()) {
                CheckForReload.schedule(config.getCheckConfigReload());
            } else {
                CheckForReload.cancel();
            }

            configureUserAgentPool(config.getDownload());
            configureDownloadTimeouts(config.getDownload());

            CheckForDownload.setJobs(config.getJobs());
            CheckForDownload.schedule(config.getCheckIntervals());

            // when not already started schedule
            HealthCheck.schedule(300);
            UpSince.schedule();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to completely reload!", e);
            return false;
        }

        configFileChecksum = CalcChecksum.checksum(CONFIG_FILE);

        logger.info(() -> "Config Reload took " + time.chooseBest() + ". Reloaded Config." +
                " checksum = " + configFileChecksum);

        return true;
    }

    /**
     * Suggests to reload the config File. Only happens when the Checksum has changed.
     *
     * @return true only when read
     */
    public static boolean read() {
        if (configFileChecksum == CalcChecksum.checksum(CONFIG_FILE))
            return false;

        return forceRead();
    }

    /**
     * Checks if automatic config file reload is enabled
     *
     * @return true when enables
     */
    public static boolean isAutomaticReload() {
        return automaticReload;
    }

    private static void configureUserAgentPool(GeneralDownloadWrapper download) {
        if (!download.getUserAgents().isEmpty()) {
            download.getUserAgents().forEach(UserAgentPool::addAgent);
        }

        if (download.getUserAgentFile() != null && download.getUserAgentFileMode() != null) {
            UserAgentPool.setUserAgentFile(download.getUserAgentFile());
            UserAgentPool.setDataFormat(download.getUserAgentFileMode());

            UserAgentPool.readAgentsFromFile();
        }

        if (download.getUserAgentReloadPeriod() != 0) {
            UserAgentPool.scheduleReadAgentsReload(download.getUserAgentReloadPeriod());
        }

        logger.fine("Loaded " + UserAgentPool.getPoolSize() + " UserAgents!");
    }

    private static void configureDownloadTimeouts(GeneralDownloadWrapper download) {
        Download.setReadTimeout(download.getReadTimeout());
        Download.setConnectionTimeout(download.getConnectionTimeout());
    }

}
