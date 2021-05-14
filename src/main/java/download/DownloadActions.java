package download;

import download.actions.DownloadAction;
import download.preactions.PreAction;
import utility.TimeDiff;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * This Class is a wrapper for multiple actions.
 */
public class DownloadActions implements DownloadAction {
    private static final Logger logger = Logger.getLogger(DownloadAction.class.getName());
    private final Set<DownloadAction> actions;
    private PreActions preActions;

    public DownloadActions(Set<DownloadAction> actions, List<PreAction> preActions) {
        this.actions = actions;
        this.preActions = new PreActions(preActions);
    }

    /**
     * Uses the default PreAction Nothing
     *
     * @param actions
     */
    public DownloadActions(Set<DownloadAction> actions) {
        this.actions = actions;
    }

    public boolean action(byte[] data, final Category category) {
        boolean success = true;
        TimeDiff time = new TimeDiff();

        if (preActions != null) {
            preActions.compute(data);
            logger.severe("PreAction took " + time.chooseBest());
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
