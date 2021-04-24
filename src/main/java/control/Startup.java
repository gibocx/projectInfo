package control;

import control.executorhandler.ExecutorHandler;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static utility.Time.getDate;


public class Startup {
    public static final Logger logger = Logger.getGlobal();
    private static final String VERSION = "0.0.1";

    public static void printSystemProperties() {
        System.out.println("ProjectInfo Version: " + VERSION);
        System.out.println("Runtime version: " + Runtime.version());
        System.out.println("Available Processors: " + Runtime.getRuntime().availableProcessors());
    }

    public static void main(String[] args) {
        System.out.print("                 _           _   ___        __       \n" +
                         " _ __  _ __ ___ (_) ___  ___| |_|_ _|_ __  / _| ___  \n" +
                         "| '_ \\| '__/ _ \\| |/ _ \\/ __| __|| || '_ \\| |_ / _ \\ \n" +
                         "| |_) | | | (_) | |  __/ (__| |_ | || | | |  _| (_) |\n" +
                         "| .__/|_|  \\___// |\\___|\\___|\\__|___|_| |_|_|  \\___/ \n" +
                         "|_|           |__/                                   \n");

        printSystemProperties();
        logger.info("Startup at " + getDate());

        ReadConfig.read();
    }

    public static void shutdown() {
        logger.info("Shutdown at " + getDate());
        ExecutorHandler.stopExecutors();
    }
}
