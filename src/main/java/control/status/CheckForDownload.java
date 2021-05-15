package control.status;

import control.Job;
import control.executorhandler.Schedulable;
import utility.TimeDiff;

import java.util.Set;
import java.util.logging.Logger;

public class CheckForDownload {
    public static final Schedulable schedule = new Schedulable(CheckForDownload::check,1, Integer.MAX_VALUE);
    private static final Logger logger = Logger.getLogger(CheckForDownload.class.getName());
    private static Set<Job> jobs;

    /**
     * Configures the given jobs to be submitted when necessary. The checks happen
     * at the configured check interval
     *
     * @param set Set of Jobs
     */
    public static void setJobs(Set<Job> set) {
        schedule.cancel();
        jobs = set;
    }

    public static void check() {
        TimeDiff time = new TimeDiff();

        for (Job job : jobs) {
            job.submitIfnNecessary();
        }

        logger.fine(() -> "Checking and submitting " + jobs.size() + " took about " + time.chooseBest());
    }
}
