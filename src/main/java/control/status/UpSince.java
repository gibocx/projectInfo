package control.status;

import control.executorhandler.Schedulable;
import utility.TimeDiff;

import java.util.logging.Logger;

/**
 * Logs the uptime of the current application
 */
public class UpSince {
    public static final Schedulable schedule = new Schedulable(UpSince::print, 300);
    private static final TimeDiff time = new TimeDiff();
    private static final Logger logger = Logger.getLogger(UpSince.class.getName());

    public static void print() {
        logger.info("Up since " + time.chooseBest());
    }
}
