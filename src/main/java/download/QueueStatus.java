package download;

import control.executorhandler.Schedulable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class QueueStatus {
    public static int RESET_TIMEOUT_S = 30;
    private static final Logger logger = Logger.getLogger(QueueStatus.class.getName());
    private final AtomicBoolean status = new AtomicBoolean(false);
    private final Schedulable schedule = new Schedulable(this::removeFromQueue);

    public boolean isInQueue() {
        return status.get();
    }

    /**
     *
     * @return true when successfully
     */
    public boolean removeFromQueue() {
        schedule.cancel();
        return status.compareAndSet(true, false);
    }

    /**
     * @return true when successfully
     */
    public boolean addWhenNotQueued() {
        boolean success = status.compareAndSet(false, true);

        if(success) {
            schedule.schedule(RESET_TIMEOUT_S);
        }

        return success;
    }
}
