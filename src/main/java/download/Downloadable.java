package download;

import download.actions.DownloadAction;
import download.actions.DownloadActions;
import download.methods.DownloadMethod;
import utility.TimeDiff;

import java.util.Set;
import java.util.logging.Logger;

public class Downloadable implements Runnable {
    private static final Logger logger = Logger.getLogger(Downloadable.class.getName());
    private final DownloadMethod method;
    private final DownloadActions action;
    private final Set<Category> categories;

    public Downloadable(DownloadMethod method, DownloadActions action, Set<Category> categories) {
        this.method = method;
        this.action = action;
        this.categories = categories;
    }

    @Override
    public void run() {
        TimeDiff time = new TimeDiff();

        method.download(categories, action);

        logger.fine(() -> "Time " + time.chooseBest());
    }

    public DownloadAction getAction() {
        return action;
    }
}
