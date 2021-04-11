package control.executorhandler;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom Thread Factory, which names the created Threads according to
 * suppliedName[nThread]. Also it provieds an UncaughtExceptionHandler which
 * simply logs the uncaught Exceptions
 */
class CustomThreadFactory implements ThreadFactory {
    final UncaughtExceptionHandler handler = new UncaughtExceptionHandler() {
        private final Logger logger = Logger.getGlobal();

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            synchronized (this) {
                logger.log(Level.SEVERE, "Uncaught Throwable in " + t.getName(), e);
            }
        }
    };
    private final String threadFactoryName;
    private int id = 1;

    /**
     * Creates a new ThreadFactory which names the Threads according to
     * suppliedName[nThread].
     *
     * @param suppliedName suppliedName
     */
    public CustomThreadFactory(String suppliedName) {
        this.threadFactoryName = suppliedName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, threadFactoryName + "[" + id++ + "]");
        t.setUncaughtExceptionHandler(handler);
        return t;
    }
}
