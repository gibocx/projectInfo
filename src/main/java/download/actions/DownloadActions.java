package download.actions;

import download.Category;
import download.preactions.PreAction;
import utility.TimeDiff;

import java.util.Set;
import java.util.logging.Logger;

/**
 * This Class is a wrapper for multiple actions.
 */
public class DownloadActions implements DownloadAction {
    private static final Logger logger = Logger.getGlobal();
    private final Set<DownloadAction> actions;
    private PreAction preAction;

    public DownloadActions(Set<DownloadAction> actions, PreAction preAction) {
        this.actions = actions;
        this.preAction = preAction;
    }

    /**
     * Uses the default PreAction Nothing
     * @param actions
     */
    public DownloadActions(Set<DownloadAction> actions) {
        this.actions = actions;
    }


    public boolean action(byte[] data, Category category) {
        boolean success = true;
        TimeDiff time = new TimeDiff();

        if(preAction != null) {
            data = preAction.compute(data);
            logger.fine("PreAction("+ preAction.getClass() +")took " + time.chooseBest());
        }

        time.reset();

        for (DownloadAction action : actions) {
            if (action.action(data, category)) {
                success = false;
            }
        }

        logger.fine("All Actions took " + time.chooseBest());

        return success;
    }

    public boolean init() {
        boolean success = true;

        for (DownloadAction action : actions) {
            if (!action.init()) {
                success = false;
            }
        }

        return success;
    }

    public boolean finish() {
        boolean success = true;

        for (DownloadAction action : actions) {
            if (!action.finish()) {
                success = false;
            }
        }

        return success;
    }
}
