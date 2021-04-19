package download.actions;

import download.Category;
import download.preactions.PreAction;

import java.util.Set;

/**
 * This Class is a wrapper for multiple actions.
 */
public class DownloadActions implements DownloadAction {
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

        if(preAction != null) {
            data = preAction.compute(data);
        }

        for (DownloadAction action : actions) {
            if (action.action(data, category)) {
                success = false;
            }
        }

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
