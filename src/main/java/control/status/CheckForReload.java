package control.status;

import control.ReadConfig;
import control.executorhandler.Schedulable;
import utility.TimeDiff;

import java.util.concurrent.Future;
import java.util.logging.Logger;

public class CheckForReload {
    public static final Schedulable schedule = new Schedulable(CheckForReload::readConfig,30,Integer.MAX_VALUE);
    private static final Logger logger = Logger.getLogger(CheckForReload.class.getName());
    private static Future<CheckForReload> future;

    public static void readConfig() {
        TimeDiff time = new TimeDiff();

        ReadConfig.read();

        logger.fine("Config reload done after " + time.chooseBest());
    }
}
