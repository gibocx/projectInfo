package control.status;

import control.Job;
import control.executorhandler.ExecutorHandler;
import utility.TimeDiff;

import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

public class CheckForDownload implements Runnable {
    private static final Logger logger = Logger.getGlobal();
    private static final int MIN_CHECK_PERIOD = 2;
    private static Set<Job> jobs;
    private static ScheduledFuture<CheckForDownload> future;

    /**
     * Configures the given jobs to be submitted when necessary. The checks happen
     * at the configured check interval
     *
     * @param set Set of Jobs
     */
    public static void setJobs(Set<Job> set) {
        cancel();
        jobs = set;
    }

    public static void schedule(int period) {
        if (future != null) {
            return;
        }

        if (period < MIN_CHECK_PERIOD) {
            period = MIN_CHECK_PERIOD;
        }

        future = (ScheduledFuture<CheckForDownload>) ExecutorHandler.scheduleAtFixedRate(new CheckForDownload(), period);
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

        for (Job job : jobs) {
            job.submitIfnNecessary();
        }

        logger.fine(() -> "Checking and submitting " + jobs.size() + " took about " + time.chooseBest());
    }
}
