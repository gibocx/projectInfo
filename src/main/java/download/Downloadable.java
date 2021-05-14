package download;

import download.methods.DownloadMethod;
import utility.TimeDiff;

import java.util.Set;
import java.util.logging.Logger;

public class Downloadable implements Runnable {
    private static final Logger logger = Logger.getLogger(Downloadable.class.getName());
    private final DownloadMethod method;
    private final DownloadActions action;
    private final Set<Category> categories;
    private final QueueStatus status;

    public Downloadable(final DownloadMethod method, final DownloadActions action, final Set<Category> categories,
                        final QueueStatus status) {
        this.method = method;
        this.action = action;
        this.categories = categories;
        this.status = status;
    }

    @Override
    public void run() {
        TimeDiff time = new TimeDiff();

        method.download(categories, action);
        logger.fine(() -> "Time " + time.chooseBest());
        status.removeFromQueue();
    }
}
