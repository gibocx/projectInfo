package control.status;

import control.executorhandler.ExecutorHandler;

import java.util.concurrent.Future;
import java.util.logging.Logger;

public class HealthCheck implements Runnable {
    private static final Logger logger = Logger.getLogger(HealthCheck.class.getName());
    private static final int MIN_PERIOD = 1;
    private static final int STD_PERIOD = 300;
    private static Future<?> future;

    public static void schedule(int period) {
        if (future != null) {
            return;
        }

        if (period < MIN_PERIOD) {
            period = MIN_PERIOD;
        }

        future = ExecutorHandler.scheduleAtFixedRate(new HealthCheck(), period, period);
    }

    public static void schedule() {
        schedule(STD_PERIOD);
    }

    public static void cancel() {
        if (future != null) {
            future.cancel(false);
            future = null;
        }
    }

    @Override
    public void run() {
        // TODO: 03.04.2021 do meaningfull health checks 
        logger.info("Health Check complete!");
    }
}
