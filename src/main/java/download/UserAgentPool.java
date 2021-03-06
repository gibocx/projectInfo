package download;

import control.executorhandler.Schedulable;
import utility.CalcChecksum;
import utility.FileStuff;
import utility.Readers;
import utility.ThreadRandom;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

public class UserAgentPool {
    public static final int MIN_RELOAD_CHECK = 60;
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36";
    private static final Logger logger = Logger.getLogger(UserAgentPool.class.getName());
    private static final long userAgentFileChecksum = 0;
    private static ArrayList<String> agents = new ArrayList<>();
    private static DataFormat dataFormat = DataFormat.NONE;
    private static File userAgentFile;
    private static final Schedulable schedule = new Schedulable(UserAgentPool::readAgentsFromFile);

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

            if (userAgentFileChecksum != CalcChecksum.checksum(file)) {
                switch (dataFormat) {
                    case PLAINTEXT:
                        Readers.readLineByLine(file, UserAgentPool::addAgent);
                        break;

                    case NONE:
                        break;
                    default:
                        logger.warning(() -> "Unknown UserAgentPool dataFormat " + dataFormat);
                        // rollback to before read
                        agents = tmp;
                        return false;
                }

                logger.fine(() -> "Loaded new UserAgents. Currently loaded : " + agents.size() + " Agents");
            } else {
                logger.fine("No reload of UserAgentFile! Same Checksum!");
            }

            return true;

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

    /**
     * Sets the dataFormat of the UserAgent file. When the {@link download.DataFormat} is null, {@link download.DataFormat#NONE} is used.
     * @param dataFormat
     */
    public static void setDataFormat(DataFormat dataFormat) {
        if (dataFormat != null) {
            UserAgentPool.dataFormat = dataFormat;
        } else {
            UserAgentPool.dataFormat = DataFormat.NONE;
            logger.info("dataFormat can not be null!");
        }
    }

    /**
     * Sets the dataFormat of the UserAgent file. When the supplied {@code String} is not available in the enum
     * {@link download.DataFormat}, {@link download.DataFormat#NONE} is used.
     * @param format Name of the DataFormat
     */
    public static void setDataFormat(String format) {
        if (format != null) {
            dataFormat = DataFormat.enumOf(format);
        } else {
            logger.info("Format can not be null!");
            dataFormat = DataFormat.NONE;
        }
    }

    /**
     * Retrieves a random userAgent, either from the specified sources or returns a standard Agent.
     * @return userAgent
     */
    public static String getUserAgent() {
        if (agents.isEmpty()) {
            return DEFAULT_USER_AGENT;
        }
        return agents.get(ThreadRandom.randAbs(agents.size()));
    }

    /**
     * Clears all added userAgents
     */
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
     * Schedules the UserAgentPool at the specified rate. When the reload period is smaller or equal to
     * 0 the reload is canceled.
     *
     * @param reloadPeriod Rate at which the reload is performed
     */
    public static void scheduleReadAgentsReload(int reloadPeriod) {
        schedule.scheduleAtFixedRate(reloadPeriod);
    }
}
