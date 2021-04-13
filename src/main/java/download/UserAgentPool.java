package download;

import control.executorhandler.ExecutorHandler;
import utility.CalcChecksum;
import utility.FileStuff;
import utility.Readers;
import utility.ThreadRandom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class UserAgentPool {
    public static final int MIN_RELOAD_CHECK = 60;
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36";
    private static final Logger logger = Logger.getGlobal();
    private static final long userAgentFileChecksum = 0;
    private static ArrayList<String> agents = new ArrayList<>();
    private static DataFormat dataFormat = DataFormat.NONE;
    private static File userAgentFile;
    private static Future<UserAgentPool> future;

    public static void addAgent(String agent) {
        if (agent != null)
            agents.add(agent);
    }

    public static int getPoolSize() {
        return agents.size();
    }

    /**
     * Reads agents from the given file. It overwrites the before configured agents. If a
     * problem occurs the agent list gets rolled back.
     *
     * @return true when successfully otherwise false
     */
    public static synchronized boolean readAgentsFromFile(File file) {
        if (FileStuff.isValid(file)) {

            // keep a copy of agents to rollback if problems occur
            ArrayList<String> tmp = new ArrayList<>(agents);
            agents.clear();

            try {
                if (userAgentFileChecksum != CalcChecksum.checksum(file)) {
                    switch (dataFormat) {
                        case PLAINTEXT:
                            Readers.readLineByLine(file,(str) -> UserAgentPool.addAgent(str));
                            break;

                        case NONE:
                            break;
                        default:
                            logger.warning(() -> "Unknown UserAgentPool dataFormat " + dataFormat);
                            // rollback to before read
                            agents = tmp;
                            return false;
                    }

                    logger.fine("Loaded new UserAgents. Currently loaded : " + agents.size());
                } else {
                    logger.fine("No reload of UserAgentFile! Same Checksum!");
                }

                return true;
            } catch (IOException ex) {
                logger.info("IOException while trying to read userAgents from file " + file.toString()
                        + " Exception :" + ex.getMessage() + "; Number of UserAgents : " + getPoolSize());

                // rollback to before read
                agents = tmp;
            }

        }

        return false;
    }

    /**
     * Reads agents from configured file. It overwrites the before configured agents. If a
     * problem occurs the agent list gets rolled back.
     *
     * @return true when successfully otherwise false
     */
    public static boolean readAgentsFromFile() {
        return readAgentsFromFile(userAgentFile);
    }

    public static void setDataFormat(DataFormat dataFormat) {
        if (dataFormat != null) {
            UserAgentPool.dataFormat = dataFormat;
        } else {
            UserAgentPool.dataFormat = DataFormat.NONE;
            logger.info("dataFormat can not be null!");
        }
    }

    public static void setDataFormat(String format) {
        if (format != null) {
            dataFormat = DataFormat.enumOf(format);
        } else {
            logger.info("Format can not be null!");
            dataFormat = DataFormat.NONE;
        }
    }

    public static String getUserAgent() {
        if (agents.isEmpty()) {
            return DEFAULT_USER_AGENT;
        }
        return agents.get(ThreadRandom.randAbs(agents.size()));
    }

    public static void clear() {
        agents.clear();
    }

    /**
     * Sets the UserAgentFile
     *
     * @param file file to set
     * @return true only when file is valid and was set
     */
    public static boolean setUserAgentFile(File file) {
        if (FileStuff.isValid(file)) {
            userAgentFile = file;
            return true;
        }

        return false;
    }

    /**
     * Schedules the UserAgentPool at the specified rate. When the reload period is
     * 0 the reload is canceled.
     *
     * @param reloadPeriod Rate at which the reload is performed
     * @return true if success
     */
    public static boolean scheduleReadAgentsReload(int reloadPeriod) {
        if (future != null) {
            if (!future.cancel(false)) {
                logger.severe("ReadAgentsReload could not cancel the ReadAgentsReload task!! ");
                return false;
            }
        }

        // Cancel automatic reload
        if (reloadPeriod == 0) {
            return true;
        }

        if (reloadPeriod < MIN_RELOAD_CHECK) {
            logger.info("UserAgent reloadPeriod was " + reloadPeriod + " sec but needs to be min " + MIN_RELOAD_CHECK + " sec");
            reloadPeriod = MIN_RELOAD_CHECK;
        }

        future = (Future<UserAgentPool>) ExecutorHandler.scheduleAtFixedRate(UserAgentPool::readAgentsFromFile, reloadPeriod);
        return true;
    }

}
