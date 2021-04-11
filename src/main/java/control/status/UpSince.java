package control.status;

import control.executorhandler.ExecutorHandler;
import utility.TimeDiff;

import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * Logs the uptime of the current application
 */
public class UpSince implements Runnable {
    private static final TimeDiff time = new TimeDiff();
    private static final Logger logger = Logger.getGlobal();
    private static final int PERIOD_SEC = 300;
    private static Future<UpSince> future;

    /**
     * Schedules the UpSince task when it is not already scheduled every {@value PERIOD_SEC} seconds.
     */
    public static void schedule() {
        if (future != null) {
            return;
        }

        future = (Future<UpSince>) ExecutorHandler.scheduleAtFixedRate(new UpSince(), PERIOD_SEC, PERIOD_SEC);
    }

    @Override
    public void run() {
        logger.info("Up since " + time.chooseBest());
    }
}
