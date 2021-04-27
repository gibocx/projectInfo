package control.executorhandler;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ExecutorHandler {
    private static final Logger logger = Logger.getGlobal();
    public static final int STD_NUM_THREADS = 1;
    public static final long SHUTDOWN_GRACE_TIME = 10000;
    private static final AtomicInteger submittedJobs = new AtomicInteger();
    private static ExecutorService executor;
    private static ScheduledThreadPoolExecutor scheduledExecutor;

    /**
     * Starts the normal Executor with a fixed ThreadPool of
     * <code>nThreads</code>. When nThreads is smaller than
     * {@value STD_NUM_THREADS} then {@value STD_NUM_THREADS}
     * are used. When the Executor is already running and this 7
     * method is called with the same nThreads as the current running
     * Executor nothing happens, else the Executor is shutdown and
     * restarted.
     *
     * @param nThreads number of Threads in the pool
     */
    public static void startExecutor(final int nThreads) {
        if (executor != null) {
            if (((ThreadPoolExecutor) executor).getPoolSize() == nThreads) {
                return;
            } else {
                completeShutdown(executor);
            }
        }

        executor = Executors.newFixedThreadPool(checkThreadCount(nThreads),
                new CustomThreadFactory("ioExecutor"));

        logger.config("IOExecutor started with " + nThreads + " threads");
    }

    /**
     * Starts the ScheduledExecutor with a fixed ThreadPool of
     * <code>nThreads</code>. When nThreads is smaller than
     * {@value STD_NUM_THREADS} then {@value STD_NUM_THREADS}
     * are used. When the Executor is already running and this 7
     * method is called with the same nThreads as the current running
     * Executor nothing happens, else the Executor is shutdown and
     * restarted.
     *
     * @param nThreads number of Threads in the pool
     */
    public static void startScheduledExecutor(int nThreads) {
        if (scheduledExecutor != null) {
            if (scheduledExecutor.getPoolSize() == nThreads) {
                return;
            } else {
                completeShutdown(scheduledExecutor);
            }
        }

        scheduledExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(checkThreadCount(nThreads),
                new CustomThreadFactory("scheduledExecutor"));
        scheduledExecutor.setRemoveOnCancelPolicy(true);
        logger.config("ScheduledExecutor started with " + nThreads + " threads");
    }

    /**
     * Safely shuts down both Executors. Each Executor is granted a
     * grace time of {@value SHUTDOWN_GRACE_TIME} MS to shutdown accordingly.
     * After that time they will be forced to stop.
     */
    public static void stopExecutors() {
        completeShutdown(executor);
        completeShutdown(scheduledExecutor);
    }

    /**
     * Submits the provided task for execution in the ExecutorService.
     *
     * @param task Runnable task to execute
     * @return true only if the task could be submitted
     */
    public static boolean submit(Runnable task) {
        if (task != null) {
            try {
                executor.execute(task);
                submittedJobs.getAndIncrement();
                return true;
            } catch (RejectedExecutionException ex) {
                logger.warning("Rejected Execution :" + ex.getMessage() + " Runnable task: " + task.getClass().getName());
            }
        }
        return false;
    }

    /**
     * Schedules the given task every x seconds
     *
     * @param task   Runnable to schedule
     * @param period period of task execution in seconds
     * @return Future of task
     */
    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable task, int period) {
        if (task == null) {
            throw new IllegalArgumentException("Runnable task can not be null!");
        }

        return scheduledExecutor.scheduleAtFixedRate(task, 0, period, TimeUnit.SECONDS);
    }

    /**
     * Schedules the given task every x seconds after an initial delay
     *
     * @param task   Runnable to schedule
     * @param period period of task execution in seconds
     * @param delay  initial delay of task execution
     * @return Future of task
     */
    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable task, int period, int delay) {
        if (task == null) {
            throw new IllegalArgumentException("Runnable task can not be null!");
        }

        return scheduledExecutor.scheduleAtFixedRate(task, delay, period, TimeUnit.SECONDS);
    }

    /**
     * Schedules the given task after specified delay
     *
     * @param task  task to schedule
     * @param delay delay in seconds after which the task is scheduled
     * @return scheduledFuture
     */
    public static ScheduledFuture<?> schedule(Runnable task, int delay) {
        return scheduledExecutor.schedule(task, delay, TimeUnit.SECONDS);
    }

    /**
     * Gets the standard number of Threads. These number of Threads
     * is at least used for each Executor.
     *
     * @return standard number of Threads
     */
    public static int getStdNumThreads() {
        return STD_NUM_THREADS;
    }

    /**
     * Gets the number of submitted task from the normal executor
     *
     * @return number of submitted task
     */
    public static int getSubmittedJobs() {
        return submittedJobs.get();
    }

    /**
     * Starts the Executors with the specified number of threads, when numThreads <= 0 then
     * STD_NUM_THREADS is used
     *
     * @param nThreads number of Threads
     */
    protected static void startExecutors(int nThreads) {
        if (nThreads <= STD_NUM_THREADS) {
            logger.warning("Invalid number of threads : " + nThreads + " defaults to " + STD_NUM_THREADS);
            nThreads = STD_NUM_THREADS;
        }

        startExecutor(nThreads);
        startScheduledExecutor(nThreads);

    }

    private static int checkThreadCount(int nThreads) {
        if (nThreads <= 0) {
            logger.warning("Invalid number of threads : " + nThreads + " defaults to " + STD_NUM_THREADS + "; Caller was " +
                    Thread.currentThread().getStackTrace()[2].getMethodName());
            nThreads = STD_NUM_THREADS;
        }

        return nThreads;
    }

    private static void completeShutdown(ExecutorService service) {
        service.shutdown();
        try {
            if (!service.awaitTermination(SHUTDOWN_GRACE_TIME, TimeUnit.MILLISECONDS))
                service.shutdownNow();
        } catch (InterruptedException ex) {
            logger.warning("Interrupted Excepting while shutting down executor service " + ex);
            Thread.currentThread().interrupt();
        }
    }
}
