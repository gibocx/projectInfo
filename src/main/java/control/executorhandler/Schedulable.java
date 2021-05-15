package control.executorhandler;

import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

public class Schedulable {
    public static final int MIN_SCHEDULE_TIME = 1;
    public static final int MAX_SCHEDULE_TIME = Integer.MAX_VALUE;
    public static final int STD_SCHEDULE_TIME = 300;
    private static final Logger logger = Logger.getLogger(Schedulable.class.getName());
    private final Runnable r;
    private ScheduledFuture<?> future;
    private int stdScheduledTime = STD_SCHEDULE_TIME;
    private int minScheduledTime = MIN_SCHEDULE_TIME;
    private int maxScheduledTime = MAX_SCHEDULE_TIME;


    public Schedulable(Runnable r) {
        this.r = r;

        checkRunnable(r);
    }

    public Schedulable(Runnable r, int stdScheduleTime) {
        this.r = r;
        this.stdScheduledTime = stdScheduleTime;

        checkRunnable(r);
        checkTime(stdScheduleTime);
    }

    public Schedulable(Runnable r, int minScheduleTime, int stdScheduledTime) {
        this.r = r;
        this.minScheduledTime = minScheduleTime;
        this.maxScheduledTime = stdScheduledTime;

        checkRunnable(r);
        checkTime(minScheduleTime);
        checkTime(stdScheduledTime);

        if(minScheduleTime > stdScheduledTime) {
            throw new IllegalArgumentException("minScheduleTime is bigger than maxScheduleTime("+ minScheduleTime +", " + stdScheduledTime +")");
        }
    }

    /**
     * Schedules with the configured standard schedule time or {@link control.executorhandler.Schedulable#STD_SCHEDULE_TIME}. Even when this time is smaller or greater than the
     * specified bounds of {@code minScheduleTime} or {@code maxScheduleTime}.
     */
    public void schedule() {
        schedule(stdScheduledTime);
    }

    public void schedule(int delay) {
        delay = checkScheduleTime(delay);
        future = ExecutorHandler.schedule(r,delay);
    }

    public void scheduleAtFixedRate(int period) {
        period = checkScheduleTime(period);
        future = ExecutorHandler.scheduleAtFixedRate(r, period, period);
    }

    public void scheduleAtFixedRate(int period, int delay) {
        period = checkScheduleTime(period);
        delay = checkScheduleTime(delay);
        future = ExecutorHandler.scheduleAtFixedRate(r, period, delay);
    }

    public void scheduleAtFixedRate() {
        future = ExecutorHandler.scheduleAtFixedRate(r, stdScheduledTime, stdScheduledTime);
    }

    public void cancel() {
        if(future != null) {
            future.cancel(false);
            future = null;
        }
    }

    public void cancel(boolean mayInterruptIfRunning) {
        if(future != null) {
            future.cancel(mayInterruptIfRunning);
            future = null;
        }
    }

    private int checkScheduleTime(int scheduleTime) {
        if(scheduleTime < minScheduledTime) {
            scheduleTime = minScheduledTime;
        } else if (scheduleTime > maxScheduledTime) {
            scheduleTime = maxScheduledTime;
        }

        return scheduleTime;
    }

    private void checkTime(int scheduleTime) throws IllegalArgumentException {
        if(scheduleTime <= 0) {
            throw new IllegalArgumentException("ScheduleTime can not be negative!(was " + scheduleTime + ")");
        }
    }

    private void checkRunnable(Runnable r) throws IllegalArgumentException {
        if(r == null) {
            throw new IllegalArgumentException("Runnable can not be null!");
        }
    }
}
