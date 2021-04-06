package control;

import control.executorhandler.ExecutorHandler;

import java.util.Scanner;
import java.util.logging.Logger;

import static utility.Time.getDate;

public class Startup {
    public static final Logger logger = Logger.getGlobal();
    private static final String VERSION = "0.0.1";

    public static void printSystemProperties() {
        System.out.println("Project info Version: " + VERSION);
        System.out.println("Runtime version: " + Runtime.version());
        System.out.println("Available Processors: " + Runtime.getRuntime().availableProcessors());
    }

    public static void main(String[] args) {
        printSystemProperties();
        logger.info("Startup at " + getDate());

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        ReadConfig.read();
    }


    public static void shutdown() {
        logger.info("Shutdown at " + getDate());
        ExecutorHandler.stopExecutors();
    }
}
