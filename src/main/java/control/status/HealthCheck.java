package control.status;

import control.executorhandler.Schedulable;

import java.util.logging.Logger;

public class HealthCheck {
    public static final Schedulable schedule = new Schedulable(HealthCheck::healthChecks,300);
    private static final Logger logger = Logger.getLogger(HealthCheck.class.getName());

    public static void healthChecks() {
        // TODO: 03.04.2021 do meaningfull health checks 
        logger.info("Health Check complete!");
    }
}
