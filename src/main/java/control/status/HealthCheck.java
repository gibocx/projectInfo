package control.status;

import control.executorhandler.ExecutorHandler;

import java.util.concurrent.Future;
import java.util.logging.Logger;

public class HealthCheck implements Runnable {
    private static Future<HealthCheck> future;
    private static final Logger logger = Logger.getGlobal();
    private static final int MIN_CHECK_TIME_SEC = 1;
    private static final int DEFAULT_CHECK_TIME_SEC = 300;

    public static void schedule(int period) {
        if (future != null) {
            return;
        }

        if(period < MIN_CHECK_TIME_SEC) {
            period = MIN_CHECK_TIME_SEC;
        }

        future = (Future<HealthCheck>) ExecutorHandler.scheduleAtFixedRate(new HealthCheck(), period, period);
    }

    public static void schedule() {
        schedule(DEFAULT_CHECK_TIME_SEC);
    }

    @Override
    public void run() {
        // TODO: 03.04.2021 do meaningfull health checks 
        logger.info("Health Check complete!");

    }
}
