package control.status;

import control.Job;
import control.ReadConfig;
import control.executorhandler.ExecutorHandler;
import utility.TimeDiff;

import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class CheckForReload implements Runnable {
    private static final Logger logger = Logger.getGlobal();
    private static final int MIN_RELOAD_PERIOD = 30;
    private static List<Job> jobs;
    private static Future<CheckForReload> future;

    public static void schedule(int period) {
        if (future != null) {
            return;
        }

        if (period < MIN_RELOAD_PERIOD) {
            period = MIN_RELOAD_PERIOD;
        }

        future = (Future<CheckForReload>) ExecutorHandler.scheduleAtFixedRate(new CheckForReload(), period, period);
    }

    /**
     * Cancels the Runnable
     */
    public static void cancel() {
        if (future != null) {
            future.cancel(false);
            future = null;
        }
    }

    @Override
    public void run() {
        TimeDiff time = new TimeDiff();

        ReadConfig.read();

        logger.fine("Config reload done after " + time.chooseBest());
    }
}
