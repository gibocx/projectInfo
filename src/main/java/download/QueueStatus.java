package download;

import control.executorhandler.ExecutorHandler;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class QueueStatus {
    public static int RESET_TIMEOUT_S = 30;
    private static final Logger logger = Logger.getLogger(QueueStatus.class.getName());
    private ScheduledFuture<QueueStatus> future;
    private final AtomicBoolean status = new AtomicBoolean(false);

    public boolean isInQueue() {
        return status.get();
    }

    /**
     *
     * @return true when successfully
     */
    public boolean removeFromQueue() {
        if(future != null) {
            future.cancel(false);
        }

        return status.compareAndSet(true, false);
    }

    /**
     * @return true when successfully
     */
    public boolean addWhenNotQueued() {
        boolean success = status.compareAndSet(false, true);

        if(success) {
            future = (ScheduledFuture<QueueStatus>) ExecutorHandler.schedule(this::removeFromQueue,RESET_TIMEOUT_S);
        }

        return success;
    }
}
